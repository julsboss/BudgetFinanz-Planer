/*document.getElementById('pac-input').addEventListener('input', function() {
    const location = this.value;
    wh_location('map', location);
});

function wh_location(my_id, my_location) {
    const string_loc = "https://maps.google.com/maps?q=" + encodeURIComponent(my_location) + "+Geldautomat&output=embed";
    const map = document.getElementById(my_id);
    map.src = string_loc;
}
function wh_location(my_id, my_location){
    var string_loc = "https://maps.google.com/maps?q="+ my_location +"+Geldautomat&output=embed";

    var map = document.getElementById(my_id);
    console.log(string_loc);
    map.src = string_loc;

    //map.src = map.src;

    document.getElementById(my_id).src = document.getElementById(my_id).src;
}*/
document.getElementById('pac-input').addEventListener('input', function() {
    const location = encodeURIComponent(this.value);
    const map = document.getElementById('map');
    map.src = `https://www.google.com/maps?q=${location}+Geldautomat&output=embed`;
}); // dieses Coding klappt aber nur in der html-Datei