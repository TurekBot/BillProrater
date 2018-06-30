package tech.ugma.brorater.warehouse;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * This informs how to marshal and unmarshal a LocalDate.
 * <p>
 * See https://stackoverflow.com/questions/36156741/marshalling-localdate-using-jaxb
 */
@SuppressWarnings("RedundantThrows")
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
