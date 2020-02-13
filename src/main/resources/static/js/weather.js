const COMPONENTID = "weather-component";

function loadWeatherStatus(cityId, lat, lon) {
    if (cityId) {
        axios.get('/weather/' + cityId)
            .then(function (response) {
                showWeatherReport(response.data, COMPONENTID);
            })
            .catch(function (error) {
                console.log(error);
                document.getElementById(COMPONENTID).innerHTML = "ERROR";
            });
    } else if (lat && lon) {

    }
}

function showWeatherReport(data, componentId) {
    console.log(data);
    //div = document.getElementById(componentId);

    city = document.getElementById("city");
    city.innerHTML = data.name;

    icon = document.getElementById("icon");
    icon.setAttribute("src", data.skyImageUri)

    sky = document.getElementById("sky");
    sky.innerHTML = data.sky + ", " + data.skyDesc;

    var extras = "Temp: " + data.temp + "°C, Real Feel: " + data.actualFeel + "°C, Hum: " + data.humidityPercentage + "%";

    if (data.cloudsPercentage) {
        extras += ", Clouds: " + data.cloudsPercentage + "%";
    }

    extra = document.getElementById("extra");
    extra.innerHTML = extras;
}
