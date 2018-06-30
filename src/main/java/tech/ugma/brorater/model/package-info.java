// This annotation is quite lovely
// It applies the given XmlJavaTypeAdapter (LocalDateAdapter) to the whole package
// This is where I found out about it: https://stackoverflow.com/questions/36156741/marshalling-localdate-using-jaxb
@XmlJavaTypeAdapters(@XmlJavaTypeAdapter(type = LocalDate.class, value = LocalDateAdapter.class))

package tech.ugma.brorater.model;

import tech.ugma.brorater.warehouse.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;