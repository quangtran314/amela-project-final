package com.amela.controller;

import com.amela.exception.NotFoundException;
import com.amela.exception.UnauthorizedException;
import com.amela.form.ContractForm;
import com.amela.form.HouseForm;
import com.amela.form.ImageForm;
import com.amela.model.Contract;
import com.amela.model.Feedback;
import com.amela.model.house.House;
import com.amela.model.house.Image;
import com.amela.model.house.Type;
import com.amela.model.user.User;

import com.amela.service.contract.IContractService;
import com.amela.service.feedback.IFeedbackService;
import com.amela.service.house.IHouseService;
import com.amela.service.house.IHouseTypeService;
import com.amela.service.image.IImageService;
import com.amela.service.user.IUserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
public class HouseController {

    @Value("${file-upload}")
    private String fileUpload;

    private final IHouseService houseService;

    private final IHouseTypeService houseTypeService;

    private final IUserService userService;

    private final IFeedbackService feedbackService;

    private final IContractService contractService;

    private final IImageService imageService;

    public HouseController(IHouseService houseService, IHouseTypeService houseTypeService, IUserService userService, IFeedbackService feedbackService, IContractService contractService, IImageService imageService) {
        this.houseService = houseService;
        this.houseTypeService = houseTypeService;
        this.userService = userService;
        this.feedbackService = feedbackService;
        this.contractService = contractService;
        this.imageService = imageService;
    }


    @ModelAttribute("house")
    public House initHouse() {
        return new House();
    }

    @ModelAttribute("user")
    public Iterable<User> initHouseTypeList() {
        return userService.findAll();
    }

    @ModelAttribute("houseTypes")
    public Iterable<Type> initUser() {
        return houseTypeService.findAll();
    }

    private String getPrincipal() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @ModelAttribute("images")
    public Iterable<Image> initHouseImages() {
        return imageService.findAll();
    }

    @GetMapping("/")
    public String defaultPage() {
        return "redirect:/houses";
    }

    //List
    @GetMapping("/houses")
    public ModelAndView listHouses(@PageableDefault(value = 4) Pageable pageable,
                                   @RequestParam("address") Optional<String> address,
                                   @RequestParam("price_from") Optional<Float> price_from,
                                   @RequestParam("price_to") Optional<Float> price_to,
                                   @RequestParam("type") Optional<Long> type) {

        ModelAndView modelAndView = new ModelAndView("/house/list");
        modelAndView.addObject("all_type", "0");

        if (address.isEmpty() && type.isEmpty() && price_from.isEmpty() && price_to.isEmpty()) {
            Page<House> houses = houseService.findAll(pageable);
            modelAndView.addObject("houses", houses);
        } else {
            String address_val = address.orElse("");
            Long type_val = type.orElse(0L);
            float price_from_val = price_from.isPresent() ? price_from.get() : 0;
            float price_to_val = price_to.isPresent() ? price_to.get() : 99999999;

            Page<House> houseList;

            Optional<Type> house_type = houseTypeService.findById(type_val);
            if (house_type.isPresent()) {
                houseList = houseService.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqualAndType(pageable, address_val, price_from_val, price_to_val, house_type.get());
                modelAndView.addObject("house_type", type_val);
            } else {
                houseList = houseService.findHouseByAddressContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(pageable, address_val, price_from_val, price_to_val);
                modelAndView.addObject("house_type", 0);
            }

            modelAndView.addObject("address", address_val);
            price_from.ifPresent(checkingPrice -> modelAndView.addObject("price_from", checkingPrice));
            price_to.ifPresent(checkingPrice -> modelAndView.addObject("price_to", checkingPrice));
            modelAndView.addObject("houses", houseList);
        }

        return modelAndView;
    }

    //Detail
    @GetMapping("/houses/{id}")
    public ModelAndView detailHouse(@PathVariable Long id) {
        Optional<House> house = houseService.findById(id);
        Optional<User> user = userService.findByEmail(getPrincipal());

        if (house.isEmpty()) {
            throw new NotFoundException();
        }
        Iterable<Image> images = imageService.findAllByHouse(house.get());

        Iterable<Feedback> feedbacks = feedbackService.findAllByHouse(house.get());

        ModelAndView modelAndView = new ModelAndView("/house/detail");
        user.ifPresent(value -> modelAndView.addObject("user", value));
        modelAndView.addObject("feedbacks", feedbacks);
        modelAndView.addObject("house", house.get());
        modelAndView.addObject("images", images);
        modelAndView.addObject("feedback", new Feedback());

        return modelAndView;
    }

    @PostMapping("/houses/{id}")
    public ModelAndView saveFeedBack(@Validated @ModelAttribute("feedback") Feedback feedBack,
                                     BindingResult bindingResult,
                                     @PathVariable Long id,
                                     Principal principal,
                                     RedirectAttributes redirect) {
        ModelAndView modelAndView = new ModelAndView("/house/detail");
        Optional<House> house = houseService.findById(id);

        if (bindingResult.hasErrors()) {
            Iterable<Image> images = imageService.findAllByHouse(house.orElseThrow());
            Iterable<Feedback> feedbacks = feedbackService.findAllByHouse(house.orElseThrow());
            modelAndView.addObject("feedbacks", feedbacks);
            modelAndView.addObject("house", house.get());
            modelAndView.addObject("images", images);

            return modelAndView;
        }

        Optional<User> user = userService.findByEmail(principal.getName());
        feedBack.setOwner(user.orElseThrow());
        feedBack.setHouse(house.orElseThrow());
        feedBack.setAmt_date(LocalDate.now());
        feedbackService.save(feedBack);
        redirect.addFlashAttribute("message", "Thank you for your feed back!");
        modelAndView.setViewName("redirect:/houses/" + id);

        return modelAndView;
    }

    //Create
    @GetMapping("/create-house")
    public ModelAndView showCreateHouse() {
        Optional<User> user = userService.findByEmail(getPrincipal());
        if (user.isEmpty()) {
            throw new UnauthorizedException();
        }
        ModelAndView modelAndView = new ModelAndView("/house/create");
        modelAndView.addObject("houseForm", new HouseForm());
        modelAndView.addObject("userCreate", user.orElseThrow());

        return modelAndView;
    }

    @PostMapping("/create-house")
    public ModelAndView saveHouse(@Validated @ModelAttribute("houseForm") HouseForm houseForm, BindingResult bindingResult) {
        Optional<User> user = userService.findByEmail(getPrincipal());
        if (user.isEmpty()) {
            throw new UnauthorizedException();
        }
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/house/create");
            modelAndView.addObject("user", user.get());
            return modelAndView;
        }

        MultipartFile multipartFile = houseForm.getSourcePath();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(houseForm.getSourcePath().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        House house = new House(houseForm.getHouse_name(),
                houseForm.getAddress(),
                houseForm.getNumBedrooms(),
                houseForm.getNumBathrooms(),
                houseForm.getDes(),
                houseForm.getPrice(),
                houseForm.getType(),
                fileName,
                houseForm.getOwner(),
                houseForm.getCancelableTime());

        houseService.save(house);
        ModelAndView modelAndView = new ModelAndView("redirect:/houses");
        modelAndView.addObject("message", "New house created successfully");

        return modelAndView;
    }

    //Renting
    @GetMapping("/house/{id}/renting")
    public ModelAndView showRentingForm(@PathVariable("id") Long houseId) {
        Optional<House> house = houseService.findById(houseId);
        ModelAndView modelAndView;

        if (house.isPresent()) {
            modelAndView = new ModelAndView("/house/rentingForm");
            modelAndView.addObject("house_id", houseId);
            modelAndView.addObject("contract_form", new ContractForm());
        } else {
            modelAndView = new ModelAndView("/error/accessDenied");
        }

        return modelAndView;
    }

    @PostMapping("/house/{id}/renting")
    public ModelAndView renting(@Validated @ModelAttribute("contract_form") ContractForm contractForm,
                                @PathVariable("id") Long houseId,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/house/rentingForm");
        }

        Optional<User> user = userService.findByEmail(getPrincipal());
        Optional<House> house = houseService.findById(houseId);
        ModelAndView modelAndView;

        if (contractService.checkContractConflict(house.orElseThrow(), contractForm.getStartDay(), contractForm.getEndDay())) {
            Contract contract = new Contract();
            contract.setStartDay(contractForm.getStartDay());
            contract.setEndDay(contractForm.getEndDay());
            contract.setDateContractSign(LocalDate.now());
            contract.setTotalPrice(contractService.getTotalPrice(house.orElseThrow().getPrice(), contractForm.getStartDay(), contractForm.getEndDay()));
            contract.setMaxPerson(contractForm.getMaxPerson());

            modelAndView = new ModelAndView("/house/renting-confirm");
            modelAndView.addObject("contract", contract);
            modelAndView.addObject("user", user.orElseThrow());
            modelAndView.addObject("house", house.orElseThrow());
            modelAndView.addObject("message", "Renting success.");
        } else {
            modelAndView = new ModelAndView("/house/rentingForm");
            modelAndView.addObject("house_id", houseId);
            modelAndView.addObject("contract_form", new ContractForm());
            modelAndView.addObject("message", "This house is rented in this time.");
        }

        return modelAndView;
    }

    @PostMapping("/house/{id}/renting-confirm")
    public ModelAndView rentingConfirm(@Validated @ModelAttribute("contract") Contract contract,
                                       @PathVariable("id") long id,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirect) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/error/accessDenied");
        }

        contractService.save(contract);
        redirect.addFlashAttribute("message", "Thanks you for choosing our house to become the place you want to rest!");
        return new ModelAndView("redirect:/houses/" + id);
    }

    //Upload image
    @GetMapping("/upload-image/{id}")
    public ModelAndView showImageForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/house/image");
        modelAndView.addObject("imageForm", new ImageForm());
        modelAndView.addObject("house_id", id);
        return modelAndView;
    }

    @PostMapping("/upload-image/{id}")
    public ModelAndView updateImage(@PathVariable Long id, @ModelAttribute ImageForm imageForm) {
        MultipartFile[] multipartFile = imageForm.getSourcePath();
        String[] fileNames = new String[multipartFile.length];

        for (int i = 0; i < multipartFile.length; i++) {
            fileNames[i] = multipartFile[i].getOriginalFilename();
        }

        try {
            for (int i = 0; i < fileNames.length; i++) {
                FileCopyUtils.copy(imageForm.getSourcePath()[i].getBytes(), new File(fileUpload + fileNames[i]));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (String fileName : fileNames) {
            Image image = new Image(imageForm.getImage_id(), imageForm.getName(),
                    fileName, imageForm.getDes(), imageForm.getHouse());
            imageService.save(image);
        }

        ModelAndView modelAndView = new ModelAndView("/house/image");
        modelAndView.addObject("imageForm", imageForm);
        modelAndView.addObject("house_id", id);
        modelAndView.addObject("message", "Update successfully !");
        return modelAndView;
    }

    //History Renting
    @GetMapping("/viewContracts")
    public ModelAndView viewContractByUserid(@PageableDefault(value = 4) Pageable pageable,
                                             @Param("houseName") Optional<String> houseName) {
        Optional<User> userOptional = userService.findByEmail(getPrincipal());
        ModelAndView modelAndView = new ModelAndView("/house/history-renting");

        Page<Contract> contract;
        if (houseName.isEmpty()) {
            contract = contractService.findAllByUser(userOptional.orElseThrow(), pageable);
        } else {
            contract = contractService.findContractByHouseNameContaining(houseName.orElseThrow(), pageable);
        }
        modelAndView.addObject("contracts", contract);

        return modelAndView;
    }

    @GetMapping("/viewContracts/{id}")
    public ModelAndView showDetailContractById(@PathVariable("id") long id) {
        Optional<Contract> contract = contractService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/house/detail_history-renting");
        modelAndView.addObject("contracts", contract.orElseThrow());

        return modelAndView;
    }

    //Manage-house
    @GetMapping("/manage-house")
    public ModelAndView showListHouseByUser(@PageableDefault(value = 4) Pageable pageable,
                                            @Param("houseName") Optional<String> houseName) {
        Optional<User> user = userService.findByEmail(getPrincipal());
        Page<House> houses = houseService.findAllByOwner(pageable, user.orElseThrow());
        ModelAndView modelAndView = new ModelAndView("/house/manage-house");
        modelAndView.addObject("houses", houses);

        return modelAndView;
    }

    @GetMapping("/manage-house-renting")
    public ModelAndView showManageHouseRenting(@PageableDefault(value = 4) Pageable pageable,
                                               @Param("houseName") Optional<String> houseName) {
        Optional<User> user = userService.findByEmail(getPrincipal());
        List<House> houses = houseService.findAllByOwner(user.orElseThrow());
        Page<Contract> contracts = contractService.findAll(pageable);

        List<Contract> list = new ArrayList<>();

        for (House h : houses) list.addAll(contractService.findAllByHouse(h, pageable).getContent());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<Contract> output = new ArrayList<>();
        if (start <= end) output = list.subList(start, end);

        Page<Contract> rentedList = new PageImpl<>(output, pageable, list.size());

        Page<Contract> contractsPage = contractService.findAllContractByHouse(user.orElseThrow(), pageable);

        ModelAndView modelAndView = new ModelAndView("/house/manage-house_renting");
        modelAndView.addObject("contracts", contractsPage);

        return modelAndView;
    }

    @GetMapping("/manage-house-free")
    public ModelAndView showManageHouseFree(@PageableDefault(value = 4) Pageable pageable,
                                            @Param("houseName") Optional<String> houseName) {
        return new ModelAndView();
    }

    //Cancel Contract House
    @GetMapping("/rented-house/{id}/cancel")
    public ModelAndView confirmCancelRentedHouse(@PathVariable("id") Long contract_id) {
        Optional<User> user = userService.findByEmail(getPrincipal());
        if (user.isEmpty()) {
            throw new UnauthorizedException();
        }

        Optional<Contract> contract = contractService.findByIdAndUser(contract_id, user.get());
        if (contract.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/house/rentedContract");
            modelAndView.addObject("contract", contract.get());
            return modelAndView;
        } else {
            throw new NotFoundException();
        }
    }

    @PostMapping("/rented-house/{id}/cancel")
    public ModelAndView cancelRentedHouse(@PathVariable("id") Long contract_id) {
        Optional<User> user = userService.findByEmail(getPrincipal());
        if (user.isEmpty()) {
            throw new UnauthorizedException();
        }

        Optional<Contract> contract = contractService.findByIdAndUser(contract_id, user.get());
        ModelAndView modelAndView;

        if (contract.isPresent()) {
            LocalDate today = LocalDate.now();
            if (contractService.getDuration(today, contract.get().getStartDay()) > contract.get().getHouse().getHouse_id()) {
                modelAndView = new ModelAndView("redirect:/houses");
                contractService.remove(contract_id);
            } else {
                modelAndView = new ModelAndView("/house/rentedContract");
                modelAndView.addObject("contract", contract.get());
                modelAndView.addObject("message", "Cannot cancel the contract because the time limit for cancellation has passed");
            }
        } else {
            throw new NotFoundException();
        }

        return modelAndView;
    }

    //Manage revenue
    @GetMapping("/manage-revenue")
    public ModelAndView manageHouseRevenue(@PageableDefault(value = 4) Pageable pageable,
                                           @RequestParam("year_value") Optional<Integer> year_value) {
        Optional<User> user = userService.findByEmail(getPrincipal());
        List<Contract> pagesContract = contractService.findListContractByHouseOwner(user.orElseThrow());

        ModelAndView modelAndView = new ModelAndView("/house/manage-revenue");
        Map<Integer, Float> priceByMonth = new HashMap<>();

        int year_val = year_value.orElseGet(() -> LocalDate.now().getYear());
        if (year_val != LocalDate.now().getYear()) {
            for (int i = 1; i <= 12; i++) {
                priceByMonth.put(i, getPriceByMonth(pagesContract, i, year_val));
            }
        } else {
            for (int i = 1; i <= LocalDate.now().getMonthValue(); i++) {
                priceByMonth.put(i, getPriceByMonth(pagesContract, i, year_val));
            }
        }

        modelAndView.addObject("price", priceByMonth);
        modelAndView.addObject("year_value", year_val);
        return modelAndView;
    }

    public float getPriceByMonth(List<Contract> contracts, int current_month, int year_val) {
        float totalPrice = 0;

        for (Contract contract : contracts) {
            if (year_val != contract.getEndDay().getYear())
                continue;
            if (contract.getEndDay().getMonthValue() == current_month && current_month < LocalDate.now().getMonthValue()) {
                totalPrice += contract.getTotalPrice();
                continue;
            }
            if (contract.getEndDay().getMonthValue() == current_month && contract.getEndDay().getDayOfMonth() < LocalDate.now().getDayOfMonth()) {
                totalPrice += contract.getTotalPrice();
            }
        }
        return totalPrice;
    }
}
