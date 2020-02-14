var ERROR_DIV = document.getElementById("error");
var ERROR_MSG = document.getElementById("error-msg");
var BUSY_DIV = document.getElementById("busy");
var WEATHER_REPORT = document.getElementById("weather-report");

function useGeolocation() {
    ERROR_DIV.setAttribute("style", "display: none;");
    BUSY_DIV.setAttribute("style", "display: none;");
    WEATHER_REPORT.setAttribute("style", "display: none;");

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(loadWeatherStatus);
    } else {
        showGlobalError("Geolocation is not supported by this browser.");
    }
}

function loadWeatherStatus(position) {
    axios.get('/weather?lat=' + position.coords.latitude + '&lon=' + position.coords.longitude)
        .then(function (response) {
            showWeatherReport(response.data);
        })
        .catch(function (error) {
            console.log(error);
            r = error.response;
            if (r.status == 422) {
                showWorkingStatus();
            } else {
                showGlobalError(r.data.msg);
            }
        });
}

function showWeatherReport(data) {
    WEATHER_REPORT.setAttribute("style", "");

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

function showWorkingStatus() {
    BUSY_DIV.setAttribute("style", "");
}

function showGlobalError(error) {
    ERROR_DIV.setAttribute("style", "");
    ERROR_MSG.innerHTML = error;
}