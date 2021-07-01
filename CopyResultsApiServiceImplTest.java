package com.resultcopy.rest.api.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.resultcopy.BabyRequest;
import com.resultcopy.rest.model.BabyResult;
import com.resultcopy.rest.model.CategoryPost;
import com.resultcopy.rest.model.CategoryPostResult;
import com.resultcopy.service.impl.BabyResultDAOImpl;

/**
 * @author AC089545
 * Test case for CopyResultsApiServiceImpl class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CopyResultsApiServiceImpl.class)
public class CopyResultsApiServiceImplTest {
  @Mock SecurityContext securityContext;
  @Mock BabyResultDAOImpl babyResultDAOImpl;

  /**
   * Test case for copy result post method asserting the response returned as true.
   * @throws Exception 
   */
  @Test
  public void testCopyResultsPost() throws Exception {
	BabyResult babyResult = new BabyResult();
    babyResult.setChildId(10);
    CategoryPost categoryPost= new CategoryPost();
    List<CategoryPost> categoryPostList=new ArrayList<>();
    categoryPost.setDisplayName("DELIVERY_INFORMATION");
    CategoryPostResult categoryPostResult=new CategoryPostResult();
    List<CategoryPostResult> categoryPostResultList=new ArrayList<>();
    categoryPostResult.setDisplayName("PREGNANCY_OUTCOME");
    categoryPostResult.setValue("VAGINAL_BIRTH");
    categoryPostResultList.add(categoryPostResult);
    categoryPost.setResult(categoryPostResultList);
    categoryPostList.add(categoryPost);
    babyResult.setCategory(categoryPostList);
    
    PowerMockito.whenNew(BabyResultDAOImpl.class).withNoArguments().thenReturn(babyResultDAOImpl);
    when(babyResultDAOImpl.createBabyResult(any(BabyRequest.class))).thenReturn(true);
    
    CopyResultsApiServiceImpl copyResultsApiService = new CopyResultsApiServiceImpl();
    Response response = copyResultsApiService.copyResultsPost(babyResult, securityContext);
    assertEquals(200, response.getStatus());
  }
}
