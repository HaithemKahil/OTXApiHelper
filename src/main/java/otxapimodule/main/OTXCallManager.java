package otxapimodule.main;

import com.alienvault.otx.connect.ConnectionUtil;
import com.alienvault.otx.connect.OTXConnection;
import com.alienvault.otx.model.indicator.Indicator;
import com.alienvault.otx.model.indicator.IndicatorType;
import com.alienvault.otx.model.pulse.Pulse;
import org.springframework.core.env.Environment;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class OTXCallManager {

    private OTXConnection otxConnection;

    public OTXConnection getOtxConnection() {
        return otxConnection;
    }

    public void setOtxConnection(OTXConnection otxConnection) {
        this.otxConnection = otxConnection;
    }

    public void setUpConnection(Environment environment){
        this.otxConnection = ConnectionUtil.getOtxConnection(environment,environment.getProperty("key"));
    }

    public ArrayList<Pulse> listAllPulses() throws MalformedURLException, URISyntaxException {
        return (ArrayList<Pulse>) otxConnection.getAllPulses();
    }

    public void setNewPulse(Pulse pulse) throws MalformedURLException, URISyntaxException {
        otxConnection.createPulse(pulse);
    }

    public ArrayList<Indicator> listPulseIndicators(String pulseId) throws MalformedURLException, URISyntaxException {
        return (ArrayList<Indicator>) otxConnection.getAllIndicatorsForPulse(pulseId);
    }

    public static Pulse getPulse(String pulseName, String pulseDescription, List<Indicator> indicators, List<String> tags, List<String> refrences, String tlp, boolean isPublic) {
        Pulse newPulse = new Pulse();
        newPulse.setName(pulseName);
        newPulse.setDescription(pulseDescription);
        newPulse.setIndicators(indicators);
        newPulse.setTags(tags);
        newPulse.setReferences(refrences);
        newPulse.setTlp(tlp);
        newPulse.setPublic(isPublic);
        return newPulse;
    }

    public static Indicator getIndicator(IndicatorType type, String indicator, String description) {
        Indicator indie = new Indicator();
        indie.setType(type);
        indie.setIndicator(indicator);
        indie.setDescription(description);
        return indie;
    }

    public static IndicatorType valueOf(String type){
        if(type.equalsIgnoreCase("IPV6")) return IndicatorType.IPV6;
        else if(type.equalsIgnoreCase("IPV4")) return IndicatorType.IPV4;
        else if(type.equalsIgnoreCase("URL")) return IndicatorType.URL;
        else if(type.equalsIgnoreCase("CIDR")) return IndicatorType.CIDR;
        else if(type.equalsIgnoreCase("CVE")) return IndicatorType.CVE;
        else if(type.equalsIgnoreCase("DOMAIN")) return IndicatorType.DOMAIN;
        else if(type.equalsIgnoreCase("EMAIL")) return IndicatorType.EMAIL;
        else if(type.equalsIgnoreCase("HOSTNAME")) return IndicatorType.HOSTNAME;
        else if(type.equalsIgnoreCase("IMPHASH")) return IndicatorType.IMPHASH;
        else if(type.equalsIgnoreCase("MD5")) return IndicatorType.MD5;
        else if(type.equalsIgnoreCase("MUTEX")) return IndicatorType.MUTEX;
        else if(type.equalsIgnoreCase("PATH")) return IndicatorType.PATH;
        else if(type.equalsIgnoreCase("PEHASH")) return IndicatorType.PEHASH;
        else if(type.equalsIgnoreCase("SHA1")) return IndicatorType.SHA1;
        else if(type.equalsIgnoreCase("SHA256")) return IndicatorType.SHA256;
        else if(type.equalsIgnoreCase("URI")) return IndicatorType.URI;
        else return null;
    }
}
