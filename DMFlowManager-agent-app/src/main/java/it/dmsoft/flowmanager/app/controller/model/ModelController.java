package it.dmsoft.flowmanager.app.controller.model;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.be.entities.Model;
import it.dmsoft.flowmanager.common.model.ModelData;
import it.dmsoft.flowmanager.framework.api.base.BaseService;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/models")
public class ModelController {

    @Resource(name = "modelService")
    private BaseService<Model, ModelData, String> modelService;

    /**
     * <p>Get all model data in the system.For production system you many want to use
     * pagination.</p>
     * @return List<ModelData>
     */
    @GetMapping
    public List<ModelData> getModels() {
        return modelService.getAll();
    }

    /**
     * Method to get the model data based on the ID.
     * @param id
     * @return ModelData
     */
    @GetMapping("/model/{id}")
    public ModelData getModel(@PathVariable String id) {
        return modelService.getById(id);
    }

    /**
     * Post request to create model information int the system.
     * @param modelData
     * @return
     */
    @PostMapping("/model")
    public ModelData saveModel(final @RequestBody ModelData modelData) {
        return modelService.save(modelData);
    }

    /**
     * <p>Delete model from the system based on the ID. The method mapping is like the getModel with difference of
     * @DeleteMapping and @GetMapping</p>
     * @param id
     * @return
     */
    @DeleteMapping("/model/{id}")
    public Boolean deleteModel(@PathVariable String id) {
        return modelService.delete(id);
    }

}