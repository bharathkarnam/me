package com.data.dao.service.mongo.daoimpl;

import com.data.dao.service.common.Common;
import com.data.dao.service.mongo.model.Transaction;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.ServerAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ComplexQueryImplTest {

    @InjectMocks
    ComplexQueryImpl complexQuery;

    MockHttpServletRequest request = new MockHttpServletRequest();

    MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);

    Transaction tr = Common.buildUserDetails();
    List<Transaction> trs = new ArrayList<Transaction>();
    MockMultipartFile mf = Common.getfile();

    @Test
    public void testinsertCSVtomongo() {
        trs.add(tr);
        FileInputStream inputFile = null;
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(mongoTemplate.insertAll(trs)).thenReturn(trs);
        boolean responseEntity = complexQuery.saveAll(trs);
        assertThat(responseEntity == true);

    }

    @Test
    public void testinsertnullCSVtomongo() {
        trs.add(tr);
        FileInputStream inputFile = null;
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(mongoTemplate.insertAll(null)).thenReturn(null);
        boolean responseEntity = complexQuery.saveAll(null);
        assertThat(responseEntity == false);

    }

    @Test
    public void testgetcalculation() throws Exception {
        DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        trs.add(tr);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(mongoTemplate.find(any(Query.class), eq(Transaction.class))).thenReturn(trs);
        double responseEntity = complexQuery.getData(tr.getToAccountId(), dateFormatter.parse("20/10/2018 12:47:55"), dateFormatter.parse("20/10/2018 13:47:55"));
        System.out.println(responseEntity);
        assertThat(responseEntity == 25.0);
    }

    @Test
    public void testgetcalculationswithnull() {
        trs.add(tr);
        FileInputStream inputFile = null;
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(mongoTemplate.insertAll(null)).thenReturn(null);
        boolean responseEntity = complexQuery.saveAll(null);
        assertThat(responseEntity == false);

    }

    @Test
    public void testgetcalculationswitherror() {
        trs.add(tr);
        FileInputStream inputFile = null;
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(mongoTemplate.insertAll(null)).thenThrow(new MongoSocketOpenException("Connection closed",new ServerAddress()));
        boolean responseEntity = complexQuery.saveAll(null);
        assertThat(responseEntity == false);

    }
}
