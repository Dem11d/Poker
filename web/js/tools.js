//задержка повтора ajax запроса 500
var ajaxDelay = 400;


$(document).ready(function(){
    //alert('');
    $("#acceptRate").click(function (){
        sendRate();
    });
    $("#showCards").click(function (){
        showCards();
    });
    $("#addBot").click(function (){
        doAction("addBot");
    });
    $("#restart").click(function (){
        doAction("restart");
    });
    
    $("#bbUp").click(function (){
         doAction("bbUp");
    });
    setInterval(function(){
        gameStatus();
    },ajaxDelay);
});
function gameStatus() {
    
    var gameStat =$('#gameStatus');
    //var chat = $('#chat');
    var step = $("#step").html();
    if(step===undefined){
        step=0;
    }
    //alert(step);
    $.ajax({
        url: "checkChanges.jsp",
        type: "POST",
        data: {step: step},
        success: function (data, textStatus, jqXHR) {
            //gameStat.html(data.trim());
            
            if(data.trim()==="true"){
                $.ajax({
                    url: "requestManager.jsp",
                    type: "POST",

                    success: function (data, textStatus, jqXHR) {
                        gameStat.html(data.trim());
                    }

                });
            }
        }
        
    });
}
function sendRate(){
    //alert('');
    rate = $("#rate").val();
    $.ajax({
        url: "setRate.jsp",
        type: "POST",
        data: {rate: rate},
        success: function (data, textStatus, jqXHR) {
            
        }
        
    });
}
function showCards(){
    var cards =$(".cards");
    result = cards.html();
    var mat = result.match( /Card{(([A-Z]|| ||[1-9])&&[^}])*}/ );
    console.log(mat);
}

function doAction(action){
    $.ajax({
        url: "gameUtils.jsp",
        type: "POST",
        data: {action: action},
        success: function (data, textStatus, jqXHR) {
            console.log(data);
        }
        
    });
}

