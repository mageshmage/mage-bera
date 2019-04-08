import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity, ICrudGetAllActionByVendor } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShipmentType, defaultValue } from 'app/shared/model/shipment-type.model';

export const ACTION_TYPES = {
  SEARCH_SHIPMENTTYPES: 'shipmentType/SEARCH_SHIPMENTTYPES',
  FETCH_SHIPMENTTYPE_LIST: 'shipmentType/FETCH_SHIPMENTTYPE_LIST',
  FETCH_SHIPMENTTYPE: 'shipmentType/FETCH_SHIPMENTTYPE',
  CREATE_SHIPMENTTYPE: 'shipmentType/CREATE_SHIPMENTTYPE',
  UPDATE_SHIPMENTTYPE: 'shipmentType/UPDATE_SHIPMENTTYPE',
  DELETE_SHIPMENTTYPE: 'shipmentType/DELETE_SHIPMENTTYPE',
  RESET: 'shipmentType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShipmentType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ShipmentTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: ShipmentTypeState = initialState, action): ShipmentTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SHIPMENTTYPES):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SHIPMENTTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_SHIPMENTTYPE):
    case REQUEST(ACTION_TYPES.DELETE_SHIPMENTTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SHIPMENTTYPES):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTTYPE):
    case FAILURE(ACTION_TYPES.CREATE_SHIPMENTTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_SHIPMENTTYPE):
    case FAILURE(ACTION_TYPES.DELETE_SHIPMENTTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SHIPMENTTYPES):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHIPMENTTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_SHIPMENTTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHIPMENTTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/shipment-types';
const apiUrlByVendor = 'api/shipment-types-byvendor';
const apiSearchUrl = 'api/_search/shipment-types';

// Actions

export const getSearchEntities: ICrudSearchAction<IShipmentType> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPMENTTYPES,
  payload: axios.get<IShipmentType>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IShipmentType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SHIPMENTTYPE_LIST,
  payload: axios.get<IShipmentType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getShipmentTypesByVendorId: ICrudGetAllActionByVendor<IShipmentType> = (vendorId, page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SHIPMENTTYPE_LIST,
  payload: axios.get<IShipmentType>(`${apiUrlByVendor}/${vendorId}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IShipmentType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTTYPE,
    payload: axios.get<IShipmentType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IShipmentType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHIPMENTTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShipmentType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPMENTTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShipmentType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPMENTTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
