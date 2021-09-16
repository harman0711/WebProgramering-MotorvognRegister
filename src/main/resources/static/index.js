$(function(){  // kjøres når dokumentet er ferdig lastet
    hentAlle();
});
function hentAlle() {
    $.get( "/hentAlle", function( biler ) {
        formaterData(biler);
    })
        .fail(function(jqXHR) {
            const json= $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });
}

function formaterData(biler) {
    let ut = "<table class='table table-striped'><tr><th>Personnr</th><th>Navn</th><th>Adresse</th>" +
        "<th>Kjennetegn</th><th>Merke</th><th>Type</th><th></th></tr>";
    for (const bil of biler) {
        ut += "<tr><td>" + bil.personnr + "</td><td>" + bil.navn + "</td><td>" + bil.adresse + "</td>" +
            "<td>" + bil.kjennetegn + "</td><td>" + bil.merke + "</td><td>" + bil.type + "</td>" + "</tr>";
    }
    ut += "</table>";
    $("#bilene").html(ut);
}

function validerL(){
    const brukernavnOK=validerBrukernavn($("#brukernavn").val());
    const passordOK=validerPassord($("#passord").val());

    if (brukernavnOK && passordOK){
        logginn();
    }
}
function logginn(){
    const enBruker={
        brukernavn: $("#brukernavn").val(),
        passord: $("#passord").val()
    }
    $.get("/logginn",enBruker, function (data){
        if (data){
            window.location.href="/innlogget.html"
        }
        else {
            $("#feilLogginn").html("Feil Brukernavn eller Passord!");
        }

    });
}
function kryp(){
    $.get("/krypter",function (data){
        if (data){
            $("#krypterM").html("Kryptering Vellykket");
        }
        else{
            $("#krypterM").html("Kryptering FEILET");
        }

    })

};