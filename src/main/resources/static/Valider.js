
function validerPersonnr(personnr){
    const regxp=/^[0-9]{11}$/;
    const ok=regxp.test(personnr);
    if (!ok){
        $("#feilPersonnr").html("Personnummeret må bestå av KUN! tall og være på 11 tall")
        return false
    }
    else {
        $("#feilPersonnr").html("")
        return true
    }
}
function validerNavn(navn){
    const regxp=/^[a-zæøåA-ZÆØÅ]{2,15}$/;
    const ok=regxp.test(navn);
    if (!ok){
        $("#feilNavn").html("Navnet må bestå av KUN! bokstaver og være mellom 2-20")
        return false;
    }
    else {
        $("#feilNavn").html("")
        return true;
    }
}
function validerAdresse(adresse){
    const regxp=/^[a-zæøåA-ZÆØÅ0-9 ]{2,30}$/;
    const ok=regxp.test(adresse);
    if (!ok){
        $("#feilAdresse").html("Adressen kan bestå av bokstaver og tall mellom 2-30 tegn")
        return false;
    }
    else {
        $("#feilAdresse").html("")
        return true
    }
}
function validerKjennetegn(kjennetegn){
    const regxp=/[A-ZÆØÅ0-9]{7}$/;
    const ok=regxp.test(kjennetegn);
    if (!ok){
        $("#feilKjennetegn").html("Kjennetegnet må bestå av tall og Bokstaver, med totalt 7 tegn")
        return false
    }
    else {
        $("#feilKjennetegn").html("")
        return true
    }
}

function validerBrukernavn(brukernavn){
    const regxB=/^[a-zæøåA-ZÆØÅ0-9]{2,15}$/;
    const ok=regxB.test(brukernavn);
    if (!ok){
        $("#feilBrukernavn").html("Navnet må bestå av KUN! bokstaver og være mellom 2-20")
        return false;
    }
    else {
        $("#feilBrukernavn").html("")
        return true;
    }

}
function validerPassord(passord){
    const regxP=/^[a-zæøåA-ZÆØÅ0-9]{2,15}$/;
    const ok=regxP.test(passord);
    if (!ok){
        $("#feilPassord").html("Navnet må bestå av KUN! bokstaver og være mellom 2-20")
        return false;
    }
    else {
        $("#feilPassord").html("")
        return true;
    }

}
