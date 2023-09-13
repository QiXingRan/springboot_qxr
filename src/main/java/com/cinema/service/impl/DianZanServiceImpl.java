package com.cinema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cinema.dao.DianZanMapper;
import com.cinema.domain.DianZan;
import com.cinema.service.IDianZanService;
import org.springframework.stereotype.Service;

@Service
public class DianZanServiceImpl extends ServiceImpl<DianZanMapper, DianZan> implements IDianZanService {
}
