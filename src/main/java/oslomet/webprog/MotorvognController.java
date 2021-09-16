package oslomet.webprog;

import org.mindrot.jbcrypt.BCrypt;
import org.omg.CORBA.INTERNAL;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class MotorvognController {


    @Autowired
    private MotorvognRepository rep;

    private Logger logger= LoggerFactory.getLogger(MotorvognController.class);
    @Autowired
    private HttpSession session;



    private boolean validerMotorvogn(Motorvogn motorvogn){
        String regxPersonnr="[0-9]{11}";
        String regxNavn="[a-zæøåA-ZÆØÅ]{2,15}";
        String regxAdresse="[a-zæøåA-ZÆØÅ0-9 ]{2,30}";
        String regxKjennetegn="[A-ZÆØÅ0-9]{7}";
        boolean personnrOk=motorvogn.getPersonnr().matches(regxPersonnr);
        boolean navnOk=motorvogn.getNavn().matches(regxNavn);
        boolean adresseOk=motorvogn.getAdresse().matches(regxAdresse);
        boolean kjennetegnOk=motorvogn.getKjennetegn().matches(regxKjennetegn);
        if (personnrOk && navnOk && adresseOk && kjennetegnOk){
            return true;
        }
        logger.error("Valideringsfeil");
        return false;
    }



    @GetMapping("/hentBiler")
    public List<Bil> hentBiler(HttpServletResponse response) throws IOException {
        List<Bil> AlleBiler=rep.hentAlleBiler();
        if (AlleBiler==null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return AlleBiler;
    }

    @PostMapping("/lagre")
    public void lagreKunde(Motorvogn motorvogn, HttpServletResponse response) throws IOException {
        if((boolean)session.getAttribute("Innlogget")) {
            if (!validerMotorvogn(motorvogn)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i validering av motorvogn- prøv igjen");
            }
            if (!rep.lagreMotorvogn(motorvogn)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Feil i DB - prøv igjen senere");
            }
        }
        else {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Kan ikke lagre,Du må være logget inn");
        }
    }

    @GetMapping("/hentAlle")
    public List<Motorvogn> hentAlleMotorvogner(HttpServletResponse response)throws IOException{
        List<Motorvogn> AlleMotorvogn=rep.hentAlleMotorvogner();
        if (AlleMotorvogn==null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return AlleMotorvogn;
    }

    @GetMapping("/slettEnMotorvogn")
    public void slettEnMotorvogn(String personnr,HttpServletResponse response) throws IOException{
        if (!rep.slettEnMotorvogn(personnr)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/hentEnKunde")
    public Motorvogn hentEnMotorvogn(String personnr, HttpServletResponse response)throws IOException{
        Motorvogn enMotorvogn=rep.hentEnMotorvogn(personnr);
        if (enMotorvogn==null){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
        return enMotorvogn;

    }

    @PostMapping("/EndreEnMotorvogn")
    public void EndreEnMotorvogn(Motorvogn motorvogn, HttpServletResponse response) throws IOException{
        if (!rep.EndreEnMotorvogn(motorvogn)){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }

    @GetMapping("/slettAlle")
    public void slettAlleMotorvogner(HttpServletResponse response) throws IOException{
        if (!rep.slettAlleMotorvogner()){
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Feil i DB - prøv igjen senere");
        }
    }



    @GetMapping("/logginn")
    public boolean logginn(Bruker bruker){
        if (rep.sjekkNavnOgPassord(bruker)){
            session.setAttribute("Innlogget",true);
            return true;
        }
            return false;


    }
    @GetMapping("/loggut")
        public void loggut(){
            session.setAttribute("Innlogget",false);
        }


    public String krypterPassord(String passord){
        String kryptertPassord = BCrypt.hashpw(passord, BCrypt.gensalt(15));
        return kryptertPassord;
    }
    public boolean settinn(Bruker enBruker){
        Bruker oppdatertBruker=new Bruker(enBruker.getBrukernavn(),krypterPassord(enBruker.getPassord()));
        if (rep.lagreKryptertP(oppdatertBruker)){
            return true;
        }
        else {
            return false;
        }

    }

    @GetMapping("/krypter")
    public boolean krypter(){
        try {
            List<Bruker> AlleBruker=rep.hentAlleBrukere();
            for (Bruker enbruker:AlleBruker){
                settinn(enbruker);
            }
            return true;
        }
        catch (Exception e){

            return false;
        }


    }



    }

