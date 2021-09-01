var activeIndex  = document.getElementsByClassName("paging__active")[0].text;
var totalPages  = document.getElementById("totalPages").innerHTML;

var pagination__prev =  document.getElementById("pagination__prev");
var pagination__next =  document.getElementById("pagination__next");
var page_prev =  document.getElementById("page_prev");
var page_current =  document.getElementById("page_current");
var page_next =  document.getElementById("page_next");

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
    page_prev.setAttribute("href","/manage-house?page="+(page_prev.text-1));
    page_current.setAttribute("href","/manage-house?page="+(page_current.text-1));
    page_next.setAttribute("href","/manage-house?page="+(page_next.text-1));

    //Update paging
    setUpBtnDefault();
}