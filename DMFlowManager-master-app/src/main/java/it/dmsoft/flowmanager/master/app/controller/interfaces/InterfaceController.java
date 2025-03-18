package it.dmsoft.flowmanager.master.app.controller.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.InterfaceData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import it.dmsoft.flowmanager.master.be.entities.Interface;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/interfaces")
public class InterfaceController {
	
	 	@Resource(name = "interfaceService")
	    private BaseService<Interface, InterfaceData, String> interfaceService;

	    /**
	     * <p>Get all origin data in the system.For production system you many want to use
	     * pagination.</p>
	     * @return List<OriginData>
	     */
	    @GetMapping
	    public List<InterfaceData> getInterfaces() {
	        return interfaceService.getAll();
	    }

	    /**
	     * Method to get the origin data based on the ID.
	     * @param id
	     * @return OriginData
	     */
	    @GetMapping("/interface/{id}")
	    public InterfaceData getInterface(@PathVariable String id) {
	        return interfaceService.getById(id);
	    }

	    /**
	     * Post request to create origin information int the system.
	     * @param interfaceData
	     * @return
	     */
	    @PostMapping("/interface")
	    public InterfaceData saveInterface(final @RequestBody InterfaceData interfaceData) {
	        return interfaceService.save(interfaceData);
	    }

	    /**
	     * <p>Delete origin from the system based on the ID. The method mapping is like the getModel with difference of
	     * @DeleteMapping and @GetMapping</p>
	     * @param id
	     * @return
	     */
	    @DeleteMapping("/interface/{id}")
	    public Boolean deleteInterface(@PathVariable String id) {
	        return interfaceService.delete(id);
	    }

}
