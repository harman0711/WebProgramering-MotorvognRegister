package oslomet.webprog;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorvognRepository {

    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(MotorvognRepository.class);

    public boolean lagreMotorvogn(Motorvogn motorvogn) {
        String sql = "INSERT INTO Motorvogn (personnr,navn,adresse,kjennetegn,merke,type) VALUES(?,?,?,?,?,?)";
        try {
            db.update(sql,motorvogn.getPersonnr(),motorvogn.getNavn(),motorvogn.getAdresse(),motorvogn.getKjennetegn(),
                    motorvogn.getMerke(),motorvogn.getType());
            return true;
        }
        catch (Exception e){
            logger.error("Feil i lagreMotorvogn");
            return false;
        }

    }

    public List<Motorvogn> hentAlleMotorvogner() {
        String sql = "SELECT * FROM Motorvogn";
        try {
            List<Motorvogn> alleMotorvogner=db.query(sql,new BeanPropertyRowMapper(Motorvogn.class));
            return alleMotorvogner;
        }
        catch (Exception e){
            logger.error("Feil i hentAlleMotorvogner");
            return null;
        }

     }

    public boolean slettEnMotorvogn(String personnr) {
        String sql = "DELETE FROM Motorvogn WHERE personnr=?";
        try{
            db.update(sql,personnr);
            return true;
        }
        catch (Exception e){
            logger.error("Feil i slettEnMotorvogn");
            return false;
        }

    }
    public Motorvogn hentEnMotorvogn(String personnr) {
        String sql = "SELECT * FROM Motorvogn WHERE personnr=?";
        try {
            Motorvogn enMotorvogn = db.queryForObject(sql,
                    BeanPropertyRowMapper.newInstance(Motorvogn.class),personnr);
            return enMotorvogn;
        }
        catch (Exception e){
            logger.error("Feil i hentEnMotorvogn");
            return null;
        }


    }

    public boolean EndreEnMotorvogn(Motorvogn motorvogn) {
        String sql = "UPDATE Motorvogn SET navn=?,adresse=? where personnr=?";
        try {
            db.update(sql,motorvogn.getNavn(),motorvogn.getAdresse(),motorvogn.getPersonnr());
            return true;
        }
        catch (Exception e){
            logger.error("Feil i EndreEnMotorvogn");
            return false;
        }

    }

    public boolean slettAlleMotorvogner () {
        String sql = "DELETE FROM Motorvogn";
        try {
            db.update(sql);
            return true;
        }
        catch (Exception e){
            logger.error("Feil i slettAlleMotorvogn");
            return false;
        }
    }

    public List<Bil> hentAlleBiler(){
        String sql = "SELECT * FROM Bil";
        try {
            List<Bil> AlleBiler=db.query(sql,new BeanPropertyRowMapper(Bil.class));
            return AlleBiler;
        }
        catch (Exception e){
            logger.error("Feil i hentAlleBiler");
            return null;
        }

    }

    public List<Bruker> hentAlleBrukere() {
        String sql = "SELECT * FROM Bruker";
        try {
            List<Bruker> alleBrukere=db.query(sql,new BeanPropertyRowMapper(Bruker.class));
            return alleBrukere;
        }
        catch (Exception e){
            logger.error("Feil i hentAlleBrukere");
            return null;
        }

    }
    public boolean lagreKryptertP(Bruker enbruker){
        String sql="UPDATE Bruker SET passord=? WHERE brukernavn=?";
        try {
            db.update(sql,enbruker.getPassord(),enbruker.getBrukernavn());
            return true;
        }
        catch (Exception e){
            logger.error("Feil i lagreKryptertP");
            return false;
        }
    }
    private boolean sjekkPassord( String passord, String hashPassord){
        boolean ok = BCrypt.checkpw(passord,hashPassord);
        return ok;
    }
    public boolean sjekkNavnOgPassord (Bruker bruker) {
        String sql = "SELECT * FROM Bruker WHERE brukernavn=?";
        try{
            Bruker dbBruker = db.queryForObject(sql,BeanPropertyRowMapper.newInstance(Bruker.class),bruker.getBrukernavn());
            return sjekkPassord(bruker.getPassord(), dbBruker.getPassord());
        }
        catch (Exception e){
            logger.error("Feil i sjekkNavnOgPassord : "+e);
            return false;
        }
    }
}
