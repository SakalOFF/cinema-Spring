function setBookedSeats(numbers){
    let rowNumber
    let seatNumber
    let seat
    let value
    for(let number of numbers){
        value = number % seatsInARow
        rowNumber = (number - ((value === 0) ? seatsInARow : value)) / seatsInARow + 1;
        seatNumber = number - (seatsInARow * (rowNumber - 1))
        seat = document.querySelector(".seat[id=\"" + rowNumber + "-" + seatNumber + "\"]")
        setBooked(seat)
    }
    if (admin){
        for(let seat of document.querySelectorAll(".seat")){
            seat.onclick = ""
            seat.style.cursor = "default"
        }
    }
}

function setBooked(seat){
    seat.setAttribute("state", 3);
    seat.setAttribute("onclick", "");
}


function chooseSeat(node){
    let state = node.getAttribute("state")
    const seatNumber = Number(node.text)
    const rowNumber = Number(node.parentElement.getAttribute("id"))
    console.dir(node);
    if (state === "1"){
        node.setAttribute("state", 2)
        node.style.background = "#fa8d00"
        basket.push((rowNumber - 1) * seatsInARow + seatNumber)
    } else {
        node.setAttribute("state", 1)
        node.style.background = "#FFD967"
        basket.splice(basket.indexOf((rowNumber - 1) * seatsInARow + seatNumber), 1)
    }
    console.dir(basket)
}

function buyTickets(){
    $.ajax({
        url: "/tickets/" + filmSessionId,
        method: "post",
        headers: {
            'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
        },
        data: {

            basket,
            filmSessionId
        }
    }).done(function (){
        document.location.reload()
    })
}

let basket = []
setBookedSeats(bookedSeats)