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
import com.sistema.sah.reservas.service.IAzureBlobStorageService;
import com.sistema.sah.reservas.service.ICrearReportePdfFacturaService;
import lombok.RequiredArgsConstructor;
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
public class CrearReportePdfFacturaService implements ICrearReportePdfFacturaService {

    private final IReservaRepository iReservaRepository;

    private final ReservaMapper reservaMapper;

    private final IFacturacionRepository iFacturacionRepository;

    private final FacturacionMapper facturacionMapper;

    private final IAzureBlobStorageService iAzureBlobStorageService;

    @Override
    public void generarReporte(String codigoUsuario) throws JRException {
        // Compila el archivo .jrxml a un archivo .jasper
        JasperReport jasperReport =  getCompiledReport("Reporte_Reservas.jrxml");
        List<ReservaDto> reservaDtos = reservaMapper.listEntityTolistDto(iReservaRepository.buscarReservasUsuario(codigoUsuario));
        for(ReservaDto reserva : reservaDtos){
            List<ReservaPdfDTO> dataReport = new ArrayList<>();
            dataReport.add(new ReservaPdfDTO(reserva, reserva.getEstadoReservaDtoFk().getNombreEstadoReserva().getDescripcion()));
            FacturacionDto facturacionDto = new FacturacionDto();
            facturacionDto.setCodigoFacturacion(generarCodigoReserva());
            facturacionDto.setEstadoFacturacion(EstadoFacturacionEnum.PENDIENTE);
            facturacionDto.setFechaCreacionFacturacion(LocalDateTime.now());
            facturacionDto.setCodigoReservaDtoFk(reserva);
            facturacionDto.setCodigoUsuarioDtoFk(UsuarioDto.builder().codigoUsuario(codigoUsuario).build());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataReport);
            // Llena el reporte con datos y parámetros (si aplica)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            facturacionDto.setUrlPdf(iAzureBlobStorageService.uploadFile(facturacionDto.getCodigoFacturacion(), pdfBytes));
            iFacturacionRepository.save(facturacionMapper.dtoToEntity(facturacionDto));
        }
    }

    private String generarCodigoReserva() {
        return String.valueOf(10000 + new Random().nextInt(90000));
    }

    private JasperReport getCompiledReport(String nombreArchivo) throws JRException {
        try (InputStream reportStream = iAzureBlobStorageService.buscarArchivo(nombreArchivo)) {
            // Compilar el archivo .jrxml desde el InputStream
            return JasperCompileManager.compileReport(reportStream);
        } catch (Exception e) {
            throw new RuntimeException("Error al compilar el reporte desde el archivo: " + nombreArchivo, e);
        }
    }

}
