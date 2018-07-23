var attempt = 3; // Variable to count number of attempts.
// Below function Executes on click of login button.
function validate(){
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    if ( username == "user" && password == "user"){
        alert ("Login successful");
        window.location = "../../templates/userProfile.html"; // Redirecting to other page.
        return false;
    }else if ( username == "admin" && password == "admin"){
        alert ("Login successful");
        window.location = "../templates/adminProfile.html"; // Redirecting to other page.
        return false;
    } else if ( username == "supervisor" && password == "supervisor"){
        alert ("Login successful");
        window.location = "blank.html"; // Redirecting to other page.
        return false;
    }
    else{
        attempt --;// Decrementing by one.
        alert("You have left "+attempt+" attempt;");
// Disabling fields after 3 attempts.
        if( attempt == 0){
            document.getElementById("username").disabled = true;
            document.getElementById("password").disabled = true;
            document.getElementById("submit").disabled = true;
            return false;
        }
    }
}