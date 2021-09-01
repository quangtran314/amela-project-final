var activeIndex  = document.getElementsByClassName("paging__active")[0].text;
var totalPages  = document.getElementById("totalPages").innerHTML;

var pagination__prev =  document.getElementById("pagination__prev");
var pagination__next =  document.getElementById("pagination__next");
var page_prev =  document.getElementById("page_prev");
var page_current =  document.getElementById("page_current");
var page_next =  document.getElementById("page_next");

//Get field in Search Bar
var searchBar_field = document.getElementsByClassName("searchBar_field");
var field_address = searchBar_field[0].value;
var field_price_from = searchBar_field[1].value;
var field_price_to = searchBar_field[2].value;
var field_type = searchBar_field[3].value;

setUpBtnDefault();

function setUpBtnDefault()
{
    if (page_prev.text < 1)
    {
        page_prev.style.display = "none";
        pagination__prev.style.display = "none";
    }
    else
    {
        page_prev.style.display = "block";
        pagination__prev.style.display = "block";
    }

    if (page_next.text > totalPages)
    {
        page_next.style.display = "none";
        pagination__next.style.display = "none";
    }
    else
    {
        page_next.style.display = "block";
        pagination__next.style.display = "block";
    }

}


function pushOn(n)
{
    //Change text on page
    page_prev.text = parseInt(page_prev.text) + n;
    page_current.text = parseInt(page_current.text) + n;
    page_next.text = parseInt(page_next.text) + n;

    // Show-hide btn pre, next
    if(page_next.text >= totalPages)
    {
        pagination__next.style.display = "none";
    }
    else if (page_prev.text <= 1)
    {
        pagination__prev.style.display = "none";
    }
    else
    {
        pagination__next.style.display = "block";
        pagination__prev.style.display = "block";
    }

    // Change state paging
    if (page_prev.text == activeIndex)
    {
        page_prev.setAttribute("class", "paging__active");
        page_current.removeAttribute("class");
        page_next.removeAttribute("class");
    }
    else if (page_current.text == activeIndex)
    {
        page_current.setAttribute("class", "paging__active");
        page_prev.removeAttribute("class");
        page_next.removeAttribute("class");
    }
    else if (page_next.text == activeIndex)
    {
        page_next.setAttribute("class", "paging__active");
        page_prev.removeAttribute("class");
        page_current.removeAttribute("class");
    }
    else
    {
        page_prev.removeAttribute("class");
        page_current.removeAttribute("class");
        page_next.removeAttribute("class");
    }

    //Change attribute of a tag
    page_prev.setAttribute("href","/houses?page="+(page_prev.text-1)+"&address="+field_address+"&price_from="+field_price_from+"&price_to="+field_price_to+"&type="+field_type);
    page_current.setAttribute("href","/houses?page="+(page_current.text-1)+"&address="+field_address+"&price_from="+field_price_from+"&price_to="+field_price_to+"&type="+field_type);
    page_next.setAttribute("href","/houses?page="+(page_next.text-1)+"&address="+field_address+"&price_from="+field_price_from+"&price_to="+field_price_to+"&type="+field_type);

    //Update paging
    setUpBtnDefault();
}