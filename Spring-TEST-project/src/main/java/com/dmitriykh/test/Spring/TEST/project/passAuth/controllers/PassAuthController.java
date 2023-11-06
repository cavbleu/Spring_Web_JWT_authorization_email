package com.dmitriykh.test.Spring.TEST.project.passAuth.controllers;

import com.dmitriykh.test.Spring.TEST.project.authorization.entity.User;
import com.dmitriykh.test.Spring.TEST.project.passAuth.entity.PassAuth;
import com.dmitriykh.test.Spring.TEST.project.authorization.security.UserDetails;
import com.dmitriykh.test.Spring.TEST.project.passAuth.services.IPassAuthService;
import com.dmitriykh.test.Spring.TEST.project.authorization.services.IUserService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pass")
public class PassAuthController {

    @Autowired
    private IPassAuthService iPassAuthService;

    @Autowired
    private IUserService iUserService;


    @GetMapping
    public String viewAllPassAuth(Model model) {
        return findPaginated(1, "name", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<PassAuth> page = iPassAuthService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<PassAuth> passAuthList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("passAuthList", passAuthList);
        return "list-passAuth";
    }

    @GetMapping("/add")
    public String showNewPasswordForm(Model model) {
        PassAuth passAuth = new PassAuth();
        model.addAttribute("passAuth", passAuth);
        return "add_passAuth";
    }

    @PostMapping("/added")
    public String showFormForSave(@ModelAttribute("passAuth") PassAuth passAuth, @AuthenticationPrincipal UserDetails userDetails) {
        passAuth.setUser(iUserService.findByEmail(userDetails.getUsername()));
        iPassAuthService.savePassAuth(passAuth);
        return "redirect:/pass/add?added_success";
    }

    @PostMapping("/upload-csv-file")
    public String uploadCSVFileForImport (@RequestParam("file") MultipartFile file,@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = iUserService.findByEmail(userDetails.getUsername());
        if (file.isEmpty()) {
            model.addAttribute("message", "Пожалуйста выберете CSV файл для импорта.");
            model.addAttribute("status", false);
            return "redirect:/pass/add?import_error";
        } else {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<PassAuth> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(PassAuth.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<PassAuth> passAuthList = csvToBean.parse();
                for (int i=0; i < passAuthList.size(); i++) {
                    passAuthList.get(i).setUser(user);
                    iPassAuthService.savePassAuth(passAuthList.get(i));
                }
                model.addAttribute("passAuth", passAuthList);
                model.addAttribute("status", true);
            } catch (Exception ex) {
                model.addAttribute("message", "Ошибка обработки CSV файла.");
                model.addAttribute("status", false);
            }
        }

        return "redirect:/pass?import_success";
    }

    @GetMapping("/upd/{id}")
    public String showUpdPasswordForm(@PathVariable("id") Long id, Model model) {
        Optional<PassAuth> passAuth = iPassAuthService.findById(id);
        model.addAttribute("passAuth", passAuth.get());
        return "upd_passAuth";
    }


    @PostMapping("/update/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, PassAuth passAuth) {
        iPassAuthService.updPassAuth(id, passAuth.getUrl(), passAuth.getName(), passAuth.getUsername(), passAuth.getPassword());
        return "redirect:/pass?update_success";
    }

    @GetMapping("/del/{id}")
    public String deletePassAuthVoid(@PathVariable (value = "id") long id)
    {
        this.iPassAuthService.delPassAuthById(id);
        return "redirect:/pass?delete_success";
    }

    @GetMapping("/del/all")
    public String deleteAllPassAuthVoid()
    {
        this.iPassAuthService.deleteAllPassAuth();
        return "redirect:/pass?delete_success";
    }
}
