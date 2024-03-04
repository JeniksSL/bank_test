package com.bankoperations.test.schedule;


import com.bankoperations.test.domain.Account;
import com.bankoperations.test.properties.DepositScheduleProperties;
import com.bankoperations.test.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Slf4j
@RequiredArgsConstructor
public class DepositSchedule {


	private final DepositScheduleProperties scheduleProperties;
	private final AccountRepository accountRepository;


	@Scheduled(cron = "${deposit.schedule.period}")
	public void deleteExpiredUsers() {
		log.info("Deposit schedule is started");
		accountRepository
				.findAll()
				.stream()
				.filter(this::depositFilter)
				.forEach(it->{
					BigDecimal valueBefore = it.getBalance();
					increaseDeposit(it);
					Account account = accountRepository.save(it);
					log.info(String.format("Account's (id = %d) balance is increased from %s to %s", it.getId(), valueBefore, account.getBalance()));
				});
	}

	private boolean depositFilter(final Account account){
		BigDecimal initialDeposit = account.getInitialDeposit();
		return initialDeposit.compareTo(BigDecimal.ZERO)>0&&
				initialDeposit
						.multiply(scheduleProperties.getMaxModifier())
						.compareTo(account.getBalance())>0;
	}
	private void increaseDeposit(final Account account){
		BigDecimal newBalance = account.getBalance().multiply(scheduleProperties.getBaseModifier());
		BigDecimal maxBalance = account.getInitialDeposit().multiply(scheduleProperties.getMaxModifier());
		newBalance=newBalance.min(maxBalance);
		account.setBalance(newBalance);
	}
}
