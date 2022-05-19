package com.manisha.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manisha.bindings.LoginForm;
import com.manisha.bindings.UnlockForm;
import com.manisha.bindings.UserForm;
import com.manisha.entity.CityMasterEntity;
import com.manisha.entity.CountryMasterEntity;
import com.manisha.entity.StateMasterEntity;
import com.manisha.entity.UserAccountEntity;
import com.manisha.repository.CityMasterRepo;
import com.manisha.repository.CountryMasterRepo;
import com.manisha.repository.StateMasterRepo;
import com.manisha.repository.UserAccountRepo;
import com.manisha.util.EmailUtils;;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private CountryMasterRepo countryMasterRepo;
	@Autowired
	private StateMasterRepo stateMasterRepo;
	@Autowired
	private CityMasterRepo cityMasterRepo;
	@Autowired
	private UserAccountRepo userAccountRepo;
	
	@Autowired
	private EmailUtils emailUtil;

	@Override
	public String logIn(LoginForm user) {

		UserAccountEntity entity = userAccountRepo.findByEmailAndPassword(user.getEmail(), user.getPwd());

		if (entity == null) {
			return "Invalid Password";
		}
		if (entity != null && entity.getAccStatus().equals("LOCKED")) {
			return "Your Account Locked";
		}

		return "SUCESS";
	}

	@Override
	public String emailcheck(String emailId) {
		UserAccountEntity entity = userAccountRepo.findByEmail(emailId);

		if (entity == null) {
			return "UNIQUE";
		}
		return "DUPLICATES";
	}

	@Override
	public Map<Integer, String> getcountry() {
		// TODO Auto-generated method stub
		List<CountryMasterEntity> countryList = countryMasterRepo.findAll();

		Map<Integer, String> countryMap = countryList.stream()
				.collect(Collectors.toMap(CountryMasterEntity::getCountryId, CountryMasterEntity::getCountryName));
		return countryMap;
	}

	@Override
	public Map<Integer, String> getState(Integer countryId) {
		// TODO Auto-generated method stub
		List<StateMasterEntity> stateList = stateMasterRepo.findByCountryId(countryId);

		Map<Integer, String> stateMap = stateList.stream()
				.collect(Collectors.toMap(StateMasterEntity::getStateId, StateMasterEntity::getStateName));

		return stateMap;
	}

	@Override
	public Map<Integer, String> getCity(Integer stateId) {
		// TODO Auto-generated method stub
		List<CityMasterEntity> cityList = cityMasterRepo.findByStateId(stateId);
		Map<Integer, String> cityMap = cityList.stream()
				.collect(Collectors.toMap(CityMasterEntity::getCityId, CityMasterEntity::getCityName));

		return cityMap;
	}

	@Override
	public String forgotPassword(String email) {
		UserAccountEntity entity = userAccountRepo.findByEmail(email);
		if (entity == null) {
			return "Invalid Email id";
				
		}
		
		String fileName="RECOVER-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		String body=readMailBodyContent(fileName,entity);
		String subject="Recover password-Ashok IT";
		boolean isSent=emailUtil.SendEmail(email, subject, body);
		if(isSent) {
			
			return "password send to register email";
		}
		return "Error";
	}

	public String generateRandomPwd() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	private String readMailBodyContent(String fileName,UserAccountEntity entity) {
		
		String mailBody=null;
		try {
			
			StringBuffer sb=new StringBuffer();
			FileReader fr=new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();//reading first line data
			
			while(line!=null) {
				
				sb.append(line);
				line=br.readLine();
			}
			
			mailBody=sb.toString();
			mailBody=mailBody.replace("{FNAME}", entity.getFirstName());
			mailBody=mailBody.replace("{LNAME}", entity.getLastName());
			mailBody=mailBody.replace("{TEMP-PWD}", entity.getPassword());
			mailBody=mailBody.replace("{EMAIL}", entity.getEmail());
			mailBody=mailBody.replace("{PWD}", entity.getPassword());
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
			return mailBody;
			
			
		
	}
	
	@Override
	public String unlockAccount(UnlockForm unlockAccForm) {
		if (!unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmNewPwd())) {

			return "Password and confirm password should be same";

		}

		UserAccountEntity entity = userAccountRepo.findByEmailAndPassword(unlockAccForm.getEmail(),
				unlockAccForm.getTempPwd());

		if (entity == null) {
			return "Incorrect Tempory Password";
		}
		entity.setPassword(unlockAccForm.getNewPwd());
		entity.setAccStatus("UNLOCKED");
		userAccountRepo.save(entity);
		return "Account Unlock";
	}

	public String registerUser(UserForm userform) {

		UserAccountEntity userAccountEntity = new UserAccountEntity();
		BeanUtils.copyProperties(userform, userAccountEntity);

//		userAccountEntity.setFirstName(user.getFirstName());
//		userAccountEntity.setLastName(user.getLastName());
//		userAccountEntity.setEmail(user.getsEmail());
//		userAccountEntity.setDob(user.getDob());
//		userAccountEntity.setPhno(user.getPhno());
//		userAccountEntity.setGender(user.getGender());
//		userAccountEntity.setCreatedDate(LocalDate.now());
//		userAccountEntity.setCountryId(user.getCityId());
//		userAccountEntity.setStateId(user.getStateId());
//		userAccountEntity.setCityId(user.getCityId());
//		userAccountEntity.setAccStatus(ACCOUNT_STATUS_LOCK);
//		// TODO Generate password

		userAccountEntity.setAccStatus("LOCKED");

		// TODO : Generate random password

		userAccountEntity.setPassword(generateRandomPwd());

		UserAccountEntity savedEntity=userAccountRepo.save(userAccountEntity);

		// TODO : logic to send Email
		
		String email=userform.getEmail();
		String subject="User registraion";
		String fileName="UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";
		String body=readMailBodyContent(fileName, userAccountEntity);
		boolean isSent=emailUtil.SendEmail(email, subject, body);
		
		if(savedEntity.getUserid()!=null && isSent) {
			
			return "SUCESS";
		}
		return "fail";

	}

}
