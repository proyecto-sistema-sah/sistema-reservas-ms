package com.sistema.sah.reservas.service.impl;

import com.sistema.sah.commons.dto.FacturacionDto;
import com.sistema.sah.commons.dto.ReservaDto;
import com.sistema.sah.commons.dto.UsuarioDto;
import com.sistema.sah.commons.helper.enums.EstadoFacturacionEnum;
import com.sistema.sah.commons.helper.mapper.FacturacionMapper;
import com.sistema.sah.commons.helper.mapper.ReservaMapper;
import com.sistema.sah.reservas.dto.ReservaPdfDTO;
import com.sistema.sah.reservas.repository.IFacturacionRepository;
import com.sistema.sah.reservas.repository.IReservaRepository;
import com.sistema.sah.reservas.service.IAzureService;
import com.sistema.sah.reservas.service.ICrearReportePdfFacturaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrearReportePdfFacturaService implements ICrearReportePdfFacturaService {

    private final IReservaRepository iReservaRepository;

    private final ReservaMapper reservaMapper;

    private final IFacturacionRepository iFacturacionRepository;

    private final FacturacionMapper facturacionMapper;

    private final IAzureService iAzureService;

    @Override
    public void generarReporte(ReservaDto reservaDto) throws JRException {
        try {
            // Compila el archivo .jrxml a un archivo .jasper
            JasperReport jasperReport = getCompiledReport("Reporte_Reservas.jrxml");
            List<ReservaPdfDTO> dataReport = new ArrayList<>();
            dataReport.add(new ReservaPdfDTO(reservaDto, reservaDto.getEstadoReservaDtoFk().getNombreEstadoReserva().getDescripcion()));

            FacturacionDto facturacionDto = new FacturacionDto();
            facturacionDto.setCodigoFacturacion(generarCodigoReserva());
            facturacionDto.setEstadoFacturacion(EstadoFacturacionEnum.PENDIENTE);
            facturacionDto.setFechaCreacionFacturacion(LocalDateTime.now());
            facturacionDto.setCodigoReservaDtoFk(reservaDto);
            facturacionDto.setCodigoUsuarioDtoFk(UsuarioDto.builder().codigoUsuario(reservaDto.getCodigoUsuarioDtoFk().getCodigoUsuario()).build());

            // Llenar el reporte
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataReport);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Exportar a PDF
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            // Subir a S3
            facturacionDto.setUrlPdf(iAzureService.uploadFile(facturacionDto.getCodigoFacturacion(), pdfBytes));

            // Guardar la facturación
            iFacturacionRepository.save(facturacionMapper.dtoToEntity(facturacionDto));
        } catch (JRException e) {
            log.error("Error generando el reporte Jasper: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el reporte Jasper", e);
        } catch (Exception e) {
            log.error("Error inesperado al generar el reporte: {}", e.getMessage(), e);
            throw new RuntimeException("Error inesperado al generar el reporte", e);
        }
    }

    private String generarCodigoReserva() {
        return String.valueOf(10000 + new Random().nextInt(90000));
    }

    private JasperReport getCompiledReport(String nombreArchivo) throws JRException {
        try {
            // Descargar y guardar el archivo temporalmente
            InputStream respuestaInput = iAzureService.buscarArchivo(nombreArchivo);

            // Compilar el archivo .jrxml desde el InputStream
            return JasperCompileManager.compileReport(respuestaInput);
        } catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw new RuntimeException("Error al compilar el reporte desde el archivo: " + nombreArchivo, e);
        }
    }

}
