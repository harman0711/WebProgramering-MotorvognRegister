$(function (){
    const personnr= window.location.search.substring(1);
    const url="/hentEnKunde?"+personnr;
    $.get(url, function (Motorvogn){
        $("#personnr").val(Motorvogn.personnr);
        $("#navn").val(Motorvogn.navn);
        $("#adresse").val(Motorvogn.adresse);
    })
        .fail(function(jqXHR) {
        const json= $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
});

function EndreEnMotorvogn() {
    const Motorvogn = {
        personnr : $("#personnr").val(),
        navn : $("#navn").val(),
        adresse : $("#adresse").val()
    }
    $.post("/EndreEnMotorvogn",Motorvogn,function(){
        window.location.href = "/innlogget.html"
    })
        .fail(function(jqXHR) {
        const json= $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}