package it.dmsoft.flowmanager.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.be.entities.Origin;
import it.dmsoft.flowmanager.common.model.OriginData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import jakarta.annotation.Resource;

//@CrossOrigin(origins = "http://localhost:4200") 
//@CrossOrigin(origins = "#{'${cors.allowed.origins}'}")
@RestController
@RequestMapping("/origins")
public class OriginController {
	
	@Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Resource(name = "originService")
    private BaseService<Origin, OriginData, String> originService;

    /**
     * <p>Get all origin data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<OriginData>
     */
    @GetMapping
    public List<OriginData> getModels() {
        return originService.getAll();
    }

    /**
     * Method to get the origin data based on the ID.
     * @param id
     * @return OriginData
     */
    @GetMapping("/origin/{id}")
    public OriginData getModel(@PathVariable String id) {
        return originService.getById(id);
    }

    /**
     * Post request to create origin information int the system.
     * @param originData
     * @return
     */
    @PostMapping("/origin")
    public OriginData saveModel(final @RequestBody OriginData originData) {
        return originService.save(originData);
    }

    /**
     * <p>Delete origin from the system based on the ID. The method mapping is like the getModel with difference of
     * @DeleteMapping and @GetMapping</p>
     * @param id
     * @return
     */
    @DeleteMapping("/origin/{id}")
    public Boolean deleteModel(@PathVariable String id) {
        return originService.delete(id);
    }

}