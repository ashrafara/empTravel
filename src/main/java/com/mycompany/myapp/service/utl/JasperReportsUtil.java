package com.mycompany.myapp.service.utl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.support.JdbcUtils;

public class JasperReportsUtil {

    private final Logger log = LoggerFactory.getLogger(JasperReportsUtil.class);
    String reportsRootDirectory;

    @Autowired
    DataSource dataSource;

    public JasperReportsUtil(String reportsRootDirectory) {
        this.reportsRootDirectory = reportsRootDirectory;
    }

    private JasperPrint jasperRunAndGet(Map<String, Object> parameters, String fileName, Connection connection)
        throws JRException, IOException {
        ClassPathResource jasperFile = new ClassPathResource(reportsRootDirectory + fileName + ".jrxml");
        return JasperFillManager.fillReport(jasperFile.getInputStream(), parameters, connection);
    }

    public byte[] getReportAsPDF(Map<String, Object> parameters, String japerFileName) {
        log.debug("JasperReportsUtil.getReportAsPDF parameters={} , fileName={} ", parameters, japerFileName);
        Connection connection = null;
        byte[] reportBytes = null;
        try {
            connection = dataSource.getConnection();

            JasperDesign design = JRXmlLoader.load(
                new File("").getAbsolutePath() + "/src/main/resources/reports/" + japerFileName + ".jrxml"
            );
            //ClassPathResource jasperFile = new ClassPathResource(reportsRootDirectory + "invoicee.jrxml");

            JasperReport report = JasperCompileManager.compileReport(design);
            report.setProperty("net.sf.jasperreports.default.pdf.font.name", "Helvetica");
            report.setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
            report.setProperty("net.sf.jasperreports.default.pdf.embedded", "false");
            JasperPrint print = JasperFillManager.fillReport(report, parameters, connection);

            reportBytes = JasperExportManager.exportReportToPdf(print);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return reportBytes;
    }

    public byte[] getReportAsXLS(Map<String, Object> parameters, String japerFileName) {
        log.debug("JasperReportsUtil.getReportAsXLS parameters={} , fileName={} ", parameters, japerFileName);
        Connection connection = null;
        byte[] reportBytes = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            connection = dataSource.getConnection();
            JasperPrint jasperPrint = jasperRunAndGet(parameters, japerFileName, connection);
            JRXlsExporter jrXlsExporter = new JRXlsExporter();
            jrXlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            jrXlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            jrXlsExporter.exportReport();
            reportBytes = byteArrayOutputStream.toByteArray();
        } catch (JRException | IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return reportBytes;
    }
}
