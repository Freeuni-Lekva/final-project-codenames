
    var mySocket = undefined;

    window.onload = () => {
        document.getElementById("chatarea").style.backgroundColor= "red";
        var mySocket = new WebSocket("ws://"+location.host + "/Codenames_war_exploded/GameplayServlet");
        mySocket.onmessage = function (event) {
            const myMessage = JSON.parse(event.data);
            document.getElementById("chatarea").style.backgroundColor= "red";
            document.getElementById("chatarea").value = myMessage;
        }
    }
    function changeBack(index, color){
        var x = document.getElementById("mytable").getElementsByTagName("td");
        var colorss = JSON.parse('${colors}');
        x[index].style.backgroundColor = colorss[index];
    }

    function sendMessage(){

        // prev = document.getElementById("chatarea").value;
        text = document.getElementById("usermessage").value + "\n";
        // value = prev + text;
        let myMessage = {
            content: text,
            type: "text"
        }
        mySocket.send(JSON.stringify(myMessage));
    }

