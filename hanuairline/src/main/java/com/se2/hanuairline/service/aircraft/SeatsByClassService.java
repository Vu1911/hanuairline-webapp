package com.se2.hanuairline.service.aircraft;

import com.se2.hanuairline.model.aircraft.AircraftType;
import com.se2.hanuairline.model.aircraft.SeatsByClass;
import com.se2.hanuairline.model.aircraft.TravelClass;
import com.se2.hanuairline.payload.aircraft.SeatsByClassPayLoad;
import com.se2.hanuairline.repository.aircraft.SeatsByClassRepository;
import com.se2.hanuairline.util.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatsByClassService {

    @Autowired
    private SeatsByClassRepository seatsByClassRepository;

    @Autowired
    private AircraftTypeService aircraftTypeService;

    @Autowired
    private TravelClassService travelClassService;

    public Page<SeatsByClass> getAll (Long aircraft_type_id, Long travelclass_id, int page, int size, String[] sort){
        Pageable pagingSort = PaginationUtils.pagingSort(page, size, sort);

        if (aircraft_type_id == null && travelclass_id == null){
            return seatsByClassRepository.findAll(pagingSort);
        }

        return seatsByClassRepository.findByAircraftType_IdOrTravelClass_Id(aircraft_type_id, travelclass_id, pagingSort);
    }

    public List<SeatsByClass> findByAircraftTypeId (Long id){
        List<SeatsByClass> seatsByClassData = seatsByClassRepository.findByAircraftType_Id(id);

        return seatsByClassData;
    }

    public SeatsByClass getById(long id){
        Optional<SeatsByClass> seatsByClassData = seatsByClassRepository.findById(id);

        if(!seatsByClassData.isPresent()){
            return null;
        }

        return seatsByClassData.get();
    }

    public SeatsByClass createSeatsByClass(SeatsByClassPayLoad request){
        AircraftType aircraftTypeData = aircraftTypeService.getAircraftTypeById(request.getAircraftType_id());
        TravelClass travelClassData = travelClassService.findById(request.getTravelClass_id());

        if(aircraftTypeData == null){
            return null;
        }

        if(travelClassData == null){
            return null;
        }

        SeatsByClass seatsByClass = new SeatsByClass();

        SeatsByClass _seatsByClass = seatsByClassRepository.save(seatsByClass);

        return _seatsByClass;
    }

    public SeatsByClass updateSeatsByClass(long id, SeatsByClassPayLoad request){
        Optional<SeatsByClass> seatsByClassData = seatsByClassRepository.findById(request.getId());
        AircraftType aircraftType = aircraftTypeService.getAircraftTypeById(request.getAircraftType_id());
        TravelClass travelClass = travelClassService.findById(request.getTravelClass_id());

        if(!seatsByClassData.isPresent()){
            return null;
        }

        if(aircraftType == null){
            return null;
        }

        if(travelClass == null){
            return null;
        }

        SeatsByClass seatsByClass = seatsByClassData.get();
        seatsByClass.setAircraftType(aircraftType);
        seatsByClass.setTravelClass(travelClass);
        seatsByClass.setQuantity(request.getQuantity());
        seatsByClass.setRowQuantity(request.getRows_quantity());

        SeatsByClass _seatsByClass = seatsByClassRepository.save(seatsByClass);

        return _seatsByClass;
    }

    public boolean deleteByAircraftType (AircraftType aircraftType){
        try{
            seatsByClassRepository.deleteAllByAircraftType(aircraftType);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
