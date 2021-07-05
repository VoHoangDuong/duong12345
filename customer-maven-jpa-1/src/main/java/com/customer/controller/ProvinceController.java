package com.customer.controller;

import com.customer.model.Customer;
import com.customer.service.customer.ICustomerService;
import com.customer.service.province.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.customer.model.Province;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProvinceController {

    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "/provinces")
    public ModelAndView listProvinces() {
        Iterable<Province> provinceList = provinceService.findAll();
        ModelAndView modelAndView = new ModelAndView("province/listP");
        modelAndView.addObject("provinces", provinceList);
        return modelAndView;
    }

    @RequestMapping(value = "/create-province")
    public ModelAndView createFormProvince(){
        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province",new Province());
        return modelAndView;
    }

    @RequestMapping(value = "/save-province",method = RequestMethod.POST)
    public ModelAndView saveProvince(@ModelAttribute("province") Province province) {
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province", new Province());
        modelAndView.addObject("message", "New province created successfully");
        return modelAndView;
    }

    @RequestMapping(value = "/edit-province/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("province/edit");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @GetMapping("/delete-province/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("province/delete");
            modelAndView.addObject("province", province.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/update-province",method = RequestMethod.POST)
    public ModelAndView updateProvince(@ModelAttribute("province") Province province) {
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("province/edit");
        modelAndView.addObject("province", province);
        modelAndView.addObject("message", "Province updated successfully");
        return modelAndView;
    }

    @PostMapping("/delete-province")
    public String deleteProvince(@ModelAttribute("province") Province province) {
        provinceService.remove(province.getId());
        return "redirect:provinces";
    }

    @GetMapping("/view-province/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Optional<Province> provinceOptional = provinceService.findById(id);
        if(!provinceOptional.isPresent()){
            return new ModelAndView("/error");
        }

        Iterable<Customer> customers = customerService.findAllByProvince(provinceOptional.get());

        ModelAndView modelAndView = new ModelAndView("province/view");
        modelAndView.addObject("province", provinceOptional.get());
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}
