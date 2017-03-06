package org.jug.algeria.controller;

import org.jug.algeria.domain.AppList;
import org.jug.algeria.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RestController
//@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeController {

  final UserRepository userRepository;

  @Inject
  public HomeController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public ModelAndView home() {
    return new ModelAndView("index");
  }

  @GetMapping(value = "/hello")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok().body("Hello there !");
  }

  @PostMapping(value = "/list/{listname}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AppList> create(@PathVariable String listname) {
	  AppList appUser = new AppList();
    appUser.setListName(listname);
    AppList saved = userRepository.save(appUser);
    return ResponseEntity.ok().body(saved);
  }

  @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AppList>> findAll() {
    final List<AppList> resultList = new ArrayList<>();
    final Iterable<AppList> all = userRepository.findAll();
    all.forEach(resultList::add);
    return ResponseEntity.ok().body(resultList);
  }
  
  @RequestMapping("/lists")
  //public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
  public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
  	System.out.println("/lists ----------?");
  	
  	String contents = "<tr><td><b>List Name::</b></td><td><b>Owner</b></td><td><b>Last Updated</b></td></tr>";
  	
  	final List<AppList> resultList = new ArrayList<>();
    final Iterable<AppList> all = userRepository.findAll();
    all.forEach(resultList::add);
    
	    for (AppList app : resultList) {
			contents = contents + "<tr><td><a href=# target='_blank' onclick=window.open('/listDetails.html','mywin','width=500,height=200'); >" + app.getListName() +"</a></td>"
			+"<td> Owner </td><td>01-13-2017</td></tr>";
		}

      return "<table border='1' width='100%'>"+contents+"</table><table>"+
      "<tr><td><a href=# target='_blank' onclick=window.open('/createpage','mywin','width=500,height=200'); >Create List</a></td></tr>"+
      "<tr><td><a href='/deleteall'>Delete All</a></td></tr></table>";
  }

}
