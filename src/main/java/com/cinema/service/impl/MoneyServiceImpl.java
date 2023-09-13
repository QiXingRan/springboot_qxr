package com.cinema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cinema.dao.MoneyMapper;
import com.cinema.domain.Money;
import com.cinema.service.IMoneyService;
import org.springframework.stereotype.Service;

@Service
public class MoneyServiceImpl extends ServiceImpl<MoneyMapper, Money> implements IMoneyService {
}
