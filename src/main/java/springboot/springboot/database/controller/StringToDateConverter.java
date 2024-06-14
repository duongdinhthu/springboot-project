package springboot.springboot.database.controller;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(MappingContext<String, Date> context) {
        String dateString = context.getSource();
        if (dateString == null) {
            return null;
        }

        // Định dạng chuỗi ngày tháng
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            // Chuyển đổi chuỗi ngày tháng thành đối tượng Date
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}