package com.resultcopy.rest.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.resultcopy.BabyResultResponse;
import com.resultcopy.PatientDetailsResponse;
import com.resultcopy.PatientResponse;
import com.resultcopy.PatientResultResponse;
import com.resultcopy.service.impl.BabyResultDAOImpl;
import com.resultcopy.service.impl.ChildDAOImpl;
import com.resultcopy.service.impl.ResultDAOImpl;
import com.resultcopy.service.serviceimpl.PatientServiceImpl;

/**
 * @author AC089545
 * Test case for CopyResultsApiServiceImpl class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PatientResultsApiServiceImpl.class)
public class PatientResultsApiServiceImplTest {
  @Mock SecurityContext securityContext;
  @Mock PatientServiceImpl patientServiceImplMock;
  @Mock ChildDAOImpl childDaoImplMock;
  @Mock ResultDAOImpl resultDAOMock;
  @Mock BabyResultDAOImpl babyResultDAOImpl;

  /**
   * Test case for asserting the patientId response.
 * @throws Exception 
   */
  @Test
  public void testPatientResultsPatientIdGet() throws Exception {
    PowerMockito.whenNew(PatientServiceImpl.class).withNoArguments().thenReturn(patientServiceImplMock);

    PatientResultResponse patientResultResponse = new PatientResultResponse();
    PatientResponse patientResponse = new PatientResponse();
    PatientDetailsResponse patientDetailsResponse = new PatientDetailsResponse();
    patientDetailsResponse.setId(123);
    patientDetailsResponse.setFirstName("FIRST_NAME");
    patientDetailsResponse.setLastName("LAST_NAME");
    patientDetailsResponse.setMrn("MRN");
    patientDetailsResponse.setFin("FIN");
    patientResponse.setPatientDetails(patientDetailsResponse);
    patientResultResponse.setPatient(patientResponse);
    when(patientServiceImplMock.getPatientDetails(any(String.class))).thenReturn(patientResultResponse);
    
    
    List<PatientDetailsResponse> patientResultResponseList = new ArrayList<>();
    patientResultResponseList.add(patientDetailsResponse);
    PowerMockito.whenNew(ChildDAOImpl.class).withNoArguments().thenReturn(childDaoImplMock);
    when(childDaoImplMock.getPatientById(any(Integer.class))).thenReturn(patientResultResponseList);
    
    PowerMockito.whenNew(ResultDAOImpl.class).withNoArguments().thenReturn(resultDAOMock);
    
    PowerMockito.whenNew(BabyResultDAOImpl.class).withNoArguments().thenReturn(babyResultDAOImpl);
    BabyResultResponse babyResultResponse = new BabyResultResponse();
    babyResultResponse.setDateTime(new Date());
    when(babyResultDAOImpl.getBabyPatientByChildId(any(Integer.class))).thenReturn(babyResultResponse);
    
    
    PatientResultsApiServiceImpl patientResultsApiService = new PatientResultsApiServiceImpl();
    Response response = patientResultsApiService.patientResultsPatientIdGet("123", securityContext);
    assertEquals(200, response.getStatus());
  }
}
