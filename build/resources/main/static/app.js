const BASE_URL = "http://localhost:8080/api/usermodel";

function getAllUsers() {
    fetch(BASE_URL + "/all")
    .then(response => response.json())
    .then(data => {
        console.log("All Users: ", data);
    })
    .catch(error => console.error("Error: ", error));
}


function addNewUser() {
    
    const nFirstName = prompt("Please enter the user's first name");
    const nLastName = prompt("Please enter the user's last name");
    const nUserName = prompt("Please enter a username");
    const nPassWord = prompt("Please enter a password");

    if (nFirstName && nLastName && nUserName && nPassWord) {
        const newUser = {
            firstname: nFirstName,
            lastname: nLastName,
            username: nUserName,
            password: nPassWord
        };

        fetch(BASE_URL + "/new", {
            method: 'POST',
            headers: {
                'Content-Type' : 'application/json',
            },
            body: JSON.stringify(newUser)
        })
            .then(response => response.json())
            .then(data => {
            console.log("Added new user: ", data);
    })
            .catch(error => console.error("Error: ", error));
    } else {
        console.error("All fields must be filled out to add a new user.");
    }
}

function updatePassword() {
    const id = prompt("Enter User ID to Update: ");
    const currentPassword = prompt("Enter Current Password: ");
    const newPassword = prompt("Enter New Password: ");

    if (id && currentPassword && newPassword) {
        fetch(BASE_URL + "/" + id)
            .then(response => response.json())
            .then(user => {
                if(user && user.password === currentPassword) {
                    const updatedUser = {
                        password: newPassword
                    };

                    fetch(BASE_URL + "/updatePassword/" + id, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(updatedUser)
                    })
                        .then(response => response.json())
                        .then(data => {
                            console.log("Updated user password: ", data);
                        })
                        .catch(error => console.error("Error: ", error));
                } else {
                    console.error("Current password does not match. Update failed");
                }
            })
            .catch(error => console.error("Error: ", error));
    } else {
        console.error("Invalid input. Please provide User ID Current Password, and New Password. ");
    }
}

function deleteUser() {
    const id = prompt("Enter User ID to Delete: ");
    fetch(BASE_URL + "/delete/" + id, {
        method: 'DELETE'
    })
        .then(() => {
            console.log("User with ID: " + id + " has been deleted.");
            return fetch(BASE_URL + "/" + id);
        })
        .then(response => {
            if(response.status === 404) {
                console.log("Confirmed: User with ID: " + id + " not found.");
            }
        })
        .catch(error => console.error("Error:", error));
}