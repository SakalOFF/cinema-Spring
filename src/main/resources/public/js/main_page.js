function sorting(select){
    const url = new URL(document.URL)
    const dayId = url.searchParams.get("day")

    const sortingParam = "?sorting=" + select.options[select.selectedIndex].value
    const dayParam = (dayId === null) ? "" : "&day=" + dayId

    let filterParam = ""
    const checkbox = document.querySelector(".filter")
    if (checkbox != null) {
        filterParam = !checkbox.checked ? "&filter=false" : "";
    }

    window.location.href = "/home" + sortingParam + dayParam + filterParam
}

function setElements(){
    const url = new URL(document.URL)
    const sorting = url.searchParams.get("sorting")

    if(sorting !== null){
        const select = document.querySelector(".field")
        if(select !== null) {
            for(let option of select.options){
                if (option.value === sorting) {
                    option.selected = true
                }
            }
        }
    }
    if (document.querySelector(".filter") !== null){
        document.querySelector(".filter").checked = url.searchParams.get("filter") !== "false"
    }
}

function filter(){
    const filtered = document.querySelector(".filter").checked

    const url = new URL(document.URL)
    const dayId = url.searchParams.get("day")
    const filmId = url.searchParams.get("film")

    const select = document.querySelector(".field")
    const sortingParam = "?sorting=" + select.options[select.selectedIndex].value
    const dayParam = (dayId === null) ? "" : "&day=" + dayId
    const filmParam = (filmId === null) ? "" : "&film=" + filmId

    window.location.href = "/home" + sortingParam + dayParam + filmParam + "&filter=" + filtered

}

function selectDay(){
    const url = new URL(document.URL)
    let dayId = Number.parseInt(url.searchParams.get("day"))
    if (isNaN(dayId)){
        dayId = todayId
    }
    const button = document.querySelector(".days-pagination").children[dayId - 1]
    button.setAttribute("state", "2")
}

setElements()
selectDay()
if (document.querySelector(".filter") === null) document.querySelector(".days-pagination").style.marginLeft = '28%'

