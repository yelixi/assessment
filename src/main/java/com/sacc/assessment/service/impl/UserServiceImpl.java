package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.ResultEnum;
import com.sacc.assessment.enums.Role;
import com.sacc.assessment.exception.BusinessException;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.UserRepository;
import com.sacc.assessment.service.UserService;
import com.sacc.assessment.util.ExcelUtils;
import com.sacc.assessment.vo.ChangePasswordVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:32
 */
@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService,UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRepository userRepository;

    /*@Resource
    private MailService mailService;*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("username=" + username);
        User u = userRepository.findByStudentId(username);
        if (u==null)
            throw new BusinessException(ResultEnum.USER_IS_NOT_EXIT);
        log.error(u.toString());
//        u.get(0).setPassword("******");
        return new UserDetail(u);
    }

    @Override
    public boolean register(User user) {
        if(userRepository.findByEmail(user.getEmail())!=null)
            throw new BusinessException(ResultEnum.EMAIL_IS_EXIT);
        if(userRepository.findByStudentId(user.getStudentId())!=null)
            throw new BusinessException(ResultEnum.STUDENT_ID_IS_EXIT);
        if(user.getRole()==null)
            user.setRole(Role.MEMBER);
        System.out.println(passwordEncoder.getClass().getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
        return true;
    }

    @Override
    public List<User> getAllUser() {
        log.error(userRepository.findAll().toString());
        return userRepository.findAll();
    }

    @Override
    public boolean updateRole(String username, Role role) {
        List<User> u = userRepository.findByUsername(username);
        u.get(0).setRole(role);
        userRepository.save(u.get(0));
        return true;
    }

    @Override
    public User getUser(Integer userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public boolean registerAll(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();  //获得上传的excel文件名
        assert fileName != null;
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1);  //获取上传的excel文件名后缀

        if("xlsx".equals(fileSuffix)) {
            log.info("excel2007及以上版本");

            XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream()); //获取excel工作簿

            XSSFSheet xssfSheet = xwb.getSheetAt(0); //获取excel的sheet

            if(xssfSheet == null) {
                return false;
            }

            //循环获取excel每一行
            for(int rowNum = 1; rowNum < xssfSheet.getLastRowNum()+1; rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if(xssfRow == null) {
                    continue;
                }

                User user = new User();

                //循环获取excel每一行的每一列
                for(int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
                    XSSFCell xssCell = xssfRow.getCell(cellNum);
                    if(xssCell == null) {
                        continue;
                    }

                    if(cellNum == 0) {
                        user.setStudentId((String)ExcelUtils.getXSSFValue(xssCell));
                    }else if(cellNum == 1) {
                        user.setUsername((String) ExcelUtils.getXSSFValue(xssCell));
                    }else if(cellNum==2){
                        user.setEmail((String) ExcelUtils.getXSSFValue(xssCell));
                    }
                    System.out.print(" "+ExcelUtils.getXSSFValue(xssCell));
                }
                String password;
                if(user.getEmail()!=null&&!user.getEmail().equals("")) {
                    password = user.getEmail() + "sacc";
                }else{
                    password = user.getStudentId() + "sacc";
                }
                user.setPassword(passwordEncoder.encode(password));
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                user.setRole(Role.MEMBER);
                //list.add(user);  //将excel每一行的数据封装到user对象,并将user对象添加到list
                userRepository.save(user);
            }
        }else if("xls".equals(fileSuffix)) {
            log.info("excel2003版本");

            Workbook wb=new HSSFWorkbook(file.getInputStream()); //获取excel工作簿

            Sheet sheet=wb.getSheetAt(0);  //获取excel的sheet

            if(sheet==null) {
                return false;
            }

            //循环获取excel每一行
            for(int rowNum=1;rowNum<sheet.getLastRowNum()+1;rowNum++) {
                Row row=sheet.getRow(rowNum);
                if(row==null) {
                    continue;
                }

                User user = new User();

                //循环获取excel每一行的每一列
                for(int cellNum=0;cellNum<row.getLastCellNum();cellNum++) {
                    Cell cell=row.getCell(cellNum);
                    if(cell==null) {
                        continue;
                    }

                    if(cellNum == 0) {
                        user.setStudentId((String)ExcelUtils.getValue(cell));
                    }else if(cellNum == 1) {
                        user.setUsername((String) ExcelUtils.getValue(cell));
                    } else if (cellNum == 2) {
                        user.setEmail((String) ExcelUtils.getValue(cell));
                    }
                    System.out.print(" "+ExcelUtils.getValue(cell));
                }
                String password;
                if(user.getEmail()!=null&&!user.getEmail().equals("")) {
                    password = user.getEmail() + "sacc";
                }else{
                    password = user.getStudentId() + "sacc";
                }
                user.setPassword(passwordEncoder.encode(password));
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                user.setRole(Role.MEMBER);
                userRepository.save(user);
            }

        }
        /*assert list != null;
        userRepository.saveAll(list);*/
        return true;
    }

    @Override
    public boolean deleteAll(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();  //获得上传的excel文件名
        assert fileName != null;
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);  //获取上传的excel文件名后缀

        if ("xlsx".equals(fileSuffix)) {
            log.info("excel2007及以上版本");

            XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream()); //获取excel工作簿

            XSSFSheet xssfSheet = xwb.getSheetAt(0); //获取excel的sheet

            if (xssfSheet == null) {
                return false;
            }

            //循环获取excel每一行
            for (int rowNum = 1; rowNum < xssfSheet.getLastRowNum() + 1; rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null) {
                    continue;
                }

                userRepository.deleteByStudentId((String) ExcelUtils.getValue(xssfRow.getCell(0)));
            }
        }else if("xls".equals(fileSuffix)) {
            log.info("excel2003版本");

            Workbook wb=new HSSFWorkbook(file.getInputStream()); //获取excel工作簿

            Sheet sheet=wb.getSheetAt(0);  //获取excel的sheet

            if(sheet==null) {
                return false;
            }

            //循环获取excel每一行
            for(int rowNum=1;rowNum<sheet.getLastRowNum()+1;rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                userRepository.deleteByStudentId((String) ExcelUtils.getValue(row.getCell(0)));
            }
        }
        return true;
    }

    @Override
    public boolean changePassword(ChangePasswordVo changePasswordVo, UserDetail userDetail) {
        User user = userRepository.getOne(userDetail.getId());
        if(!changePasswordVo.getNewPassword().equals(changePasswordVo.getConfirmPassword()))
            throw new BusinessException(ResultEnum.NEW_PASSWORD_ID_NOT_EQUAL_CONFIRM_PASSWORD);
        if(passwordEncoder.matches(changePasswordVo.getOldPassword(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordVo.getNewPassword()));
            userRepository.save(user);
        }else
            throw new BusinessException(ResultEnum.OLD_PASSWORD_ERROR);
        return true;
    }

}

