package com.itxiaoxu.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.itxiaoxu.dao.MemberDao;
import com.itxiaoxu.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public List<Integer> findMemberCountByDate(List<String> date) {
        List<Integer> memberCount = new ArrayList<>();
        for (String s : date) {
            String months = s + ".31";
            /*months = months.replaceAll(".", "-");*/
            Integer countBeforeDate = memberDao.findMemberCountBeforeDate(months);
            memberCount.add(countBeforeDate);
        }

        return memberCount;
    }
}
