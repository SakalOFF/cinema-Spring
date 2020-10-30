function changeLocale(selectedLocale){
    const url = new URL(document.URL)
    url.searchParams.delete("lang")

    url.searchParams.append("lang", selectedLocale.value)
    window.location.href = url
}

function init(){
    const localeSelect = document.querySelector(".locale")
    for(let i = 0; i < localeSelect.options.length; i++){
        if (localeSelect.options[i].value === currentLocale){
            localeSelect.selectedIndex = i
            break
        }
    }
}

init()