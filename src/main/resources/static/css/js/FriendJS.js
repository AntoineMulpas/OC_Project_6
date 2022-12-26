export function displayFriendList() {
    fetch("http://localhost:8080/api/v1/relations/list")
        .then(res => res.json())
        .then(data => console.log(data))
}