function wh_location(my_id, my_location){
    var string_loc = "https://maps.google.com/maps?q="+ my_location +"+Geldautomat&output=embed";

    var map = document.getElementById(my_id);
    console.log(string_loc);
    map.src = string_loc;

    //map.src = map.src;

    document.getElementById(my_id).src = document.getElementById(my_id).src;
}

