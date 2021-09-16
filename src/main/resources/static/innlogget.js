$(function(){  // kjøres når dokumentet er ferdig lastet
    hentAlleBiler();
    hentAlle();
});
function validerOgReg(){
    const personnrOk=validerPersonnr($("#personnr").val());
    const navnOk=validerNavn($("#navn").val());
    const adresseOk=validerAdresse($("#adresse").val());
    const kjennetegnOk=validerKjennetegn($("#kjennetegn").val());
    if (personnrOk && navnOk && adresseOk && kjennetegnOk){
        regMotorvogn()
    }
}


function regMotorvogn() {
    const motorvogn = {
        personnr : $("#personnr").val(),
        navn : $("#navn").val(),
        adresse : $("#adresse").val(),
        kjennetegn : $("#kjennetegn").val(),
        merke : $("#valgtMerke").val(),
        type : $("#valgtType").val(),
    };
    $.post("/lagre", motorvogn, function(){
        hentAlle();
    })
        .fail(function(jqXHR) {
            const json= $.parseJSON(jqXHR.responseText);
            $("#feil").html(json.message);
        });

    $("#personnr").val("");
    $("#navn").val("");
    $("#adresse").val("");
    $("#kjennetegn").val("");
    $("#valgtMerke").val("");
    $("#valgtType").val("");


}

function hentAlleBiler() {
    $.get( "/hentBiler", function( biler ) {
        formaterBiler(biler);
    })
        .fail(function(jqXHR) {
        const json= $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}

function formaterBiler(biler){
    let ut = "<select id='valgtMerke' onchange='finnTyper()'>";
    let i = 0;
    let forrigeMerke = "";
    ut+="<option>Velg merke</option>";
    for (const bil of biler){
        if(bil.merke != forrigeMerke){
            ut+="<option>"+bil.merke+"</option>";
        }
        forrigeMerke = bil.merke;
    }
    ut+="</select>";
    $("#merke").html(ut);
}

function finnTyper(){
    const valgtMerke = $("#valgtMerke").val();
    $.get( "/hentBiler", function( biler ) {
        formaterTyper(biler,valgtMerke);
    })
        .fail(function(jqXHR) {
        const json= $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}
function formaterTyper(biler,valgtMerke){
    let ut = "<select id='valgtType'>";
    for(const bil of biler ){
        if(bil.merke === valgtMerke){
            ut+="<option>"+bil.type+"</option>";
        }
    }
    ut+="</select>";
    $("#type").html(ut);
}

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
            "<td>" + bil.kjennetegn + "</td><td>" + bil.merke + "</td><td>" + bil.type + "</td>" +
            "<td> <button class='btn btn-danger' onclick='slettEnMotorvogn("+bil.personnr+")'>Slett</button></td>"+
            "<td> <a class='btn btn-primary' href='endre.html?personnr="+bil.personnr+"'>Endre</a></td>"+
            "</tr>";
    }
    ut += "</table>";
    $("#bilene").html(ut);
}

function slettEnMotorvogn(personnr) {
    const url = "/slettEnMotorvogn?personnr="+personnr;
    $.get( url, function() {
        hentAlle();
    })
        .fail(function(jqXHR) {
        const json= $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}


function slettAlle() {
    $.get( "/slettAlle", function() {
        hentAlle();
    })
        .fail(function(jqXHR) {
        const json= $.parseJSON(jqXHR.responseText);
        $("#feil").html(json.message);
    });
}
function loggut(){
    $.get("/loggut",function (){
        window.location.href="/"
    });
}



