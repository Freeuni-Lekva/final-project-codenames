// var socket = undefined
// var users = document.querySelector("#users");
// window.onload = () => {
//     var room = JSON.parse('${JSON}');
//     socket = new WebSocket("ws://"+location.host + "/Codenames_war_exploded/room?roomID="+room.ID);
//     socket.onopen = onOpen;
//     socket.onmessage = onMessage;
//     socket.onclose = onClose;
// }
//
// function onOpen(e){
//     console.log("opened");
//     const message = e.data;
//     var roomObject = JSON.parse('${JSON}');
//     var owner = roomObject.owner.user.username;
//     var li = document.createElement("li");
//     li.id = owner;
//     li.textContent = owner;
//     li.style.color = "black";
//     li.style.fontSize = "35px";
//     var fragment = document.createDocumentFragment();
//     fragment.appendChild(li);
//     users.appendChild(fragment);
// }
//
// function onMessage(e){
//     const message = e.data;
//     while(users.hasChildNodes()){
//         users.removeChild(users.lastChild);
//     }
//     var type = e.data.substr(0, e.data.indexOf(" "));
//     if(type == "NewUser"){
//         var data = JSON.parse(e.data.substr(e.data.indexOf(" ") + 1));
//         var fragment = document.createDocumentFragment();
//         for(var i=0; i<data.allPlayers.length; i++){
//             var name = data.allPlayers[i].user.username;
//             var li = document.createElement("li");
//             li.id = name;
//             li.textContent = name;
//             li.style.color = "black";
//             li.style.fontSize = "35px";
//             fragment.appendChild(li);
//         }
//         users.appendChild(fragment);
//     }
// }
//
// function onClose(e){
//     users.innerHTML = '';
// }