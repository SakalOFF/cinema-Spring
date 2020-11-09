function selectDay(){
    const url = new URL(document.URL)
    let dayId = Number.parseInt(url.searchParams.get("day"))
    if (isNaN(dayId)){
        dayId = todayId
    }
    const button = document.querySelector(".days-pagination").children[dayId - 1]
    button.setAttribute("state", "2")
}

function deleteSession(sessionId){
    $.ajax({
        url: "/redactor/sessions",
        method: "delete",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        },
        data: {
            sessionId
        }
    }).done(function (){
        document.location.reload()
    })
}


selectDay()

$('.card').hover(function(){
    $(this).find('.close').animate({opacity:1},100)
        },function(){
            $(this).find('.close').animate({opacity:0},100)
        }
)

