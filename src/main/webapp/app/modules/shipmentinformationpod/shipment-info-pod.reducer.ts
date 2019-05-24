import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity, ICrudSearchAction } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShipmentInfoPOD, defaultValue } from 'app/shared/model/shipment-info-pod.model';

export const ACTION_TYPES = {
  SEARCH_SHIPMENTINFOPODS: 'shipmentInfoSoloPOD/SEARCH_SHIPMENTINFOPODS',
  FETCH_SHIPMENTINFOPOD_LIST: 'shipmentInfoSoloPOD/FETCH_SHIPMENTINFOPOD_LIST',
  FETCH_SHIPMENTINFOPOD: 'shipmentInfoSoloPOD/FETCH_SHIPMENTINFOPOD',
  CREATE_SHIPMENTINFOPOD: 'shipmentInfoSoloPOD/CREATE_SHIPMENTINFOPOD',
  UPDATE_SHIPMENTINFOPOD: 'shipmentInfoSoloPOD/UPDATE_SHIPMENTINFOPOD',
  DELETE_SHIPMENTINFOPOD: 'shipmentInfoSoloPOD/DELETE_SHIPMENTINFOPOD',
  SET_BLOB: 'shipmentInfoSoloPOD/SET_BLOB',
  RESET: 'shipmentInfoSoloPOD/RESET',
  RESET_FOR_NEW: 'shipmentInfoSoloPOD/RESET_FOR_NEW',
  UPDATE_SEARCH: 'shipmentInfoSoloPOD/UPDATE_SEARCH',
  NEGATE_SHIPMENTINFORMATIONPOD: 'shipmentInfoSoloPOD/NEGATE_SHIPMENTINFORMATIONPOD'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShipmentInfoPOD>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
  search: '',
  isEnable: false
};

export type ShipmentInfoPODState = Readonly<typeof initialState>;

// Reducer

export default (state: ShipmentInfoPODState = initialState, action): ShipmentInfoPODState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SHIPMENTINFOPODS):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTINFOPOD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTINFOPOD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SHIPMENTINFOPOD):
    case REQUEST(ACTION_TYPES.UPDATE_SHIPMENTINFOPOD):
    case REQUEST(ACTION_TYPES.DELETE_SHIPMENTINFOPOD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SHIPMENTINFOPODS):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTINFOPOD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTINFOPOD):
    case FAILURE(ACTION_TYPES.CREATE_SHIPMENTINFOPOD):
    case FAILURE(ACTION_TYPES.UPDATE_SHIPMENTINFOPOD):
    case FAILURE(ACTION_TYPES.DELETE_SHIPMENTINFOPOD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
        isEnable: false
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SHIPMENTINFOPODS):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTINFOPOD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTINFOPOD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHIPMENTINFOPOD):
    case SUCCESS(ACTION_TYPES.UPDATE_SHIPMENTINFOPOD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHIPMENTINFOPOD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    case ACTION_TYPES.RESET_FOR_NEW:
      return {
        ...state,
        entity: {}
      };
    case ACTION_TYPES.UPDATE_SEARCH:
      return {
        ...state,
        search: action.payload,
        isEnable: true
      };
    case ACTION_TYPES.NEGATE_SHIPMENTINFORMATIONPOD:
      return {
        ...state,
        entity: {},
        entities: [],
        search: '',
        isEnable: false
      };
    default:
      return state;
  }
};

const apiUrl = 'api/shipment-info-pods';
const apiSearchUrl = 'api/shipment-info-podssearch';

// Actions

export const getSearchEntities: ICrudSearchAction<IShipmentInfoPOD> = (query, vendorId, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPMENTINFOPODS,
  payload: axios.get<IShipmentInfoPOD>(`${apiSearchUrl}?query=${query}&vendorId=${vendorId}`)
});

export const getEntities: ICrudGetAllAction<IShipmentInfoPOD> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SHIPMENTINFOPOD_LIST,
  payload: axios.get<IShipmentInfoPOD>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IShipmentInfoPOD> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTINFOPOD,
    payload: axios.get<IShipmentInfoPOD>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IShipmentInfoPOD> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHIPMENTINFOPOD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  //dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShipmentInfoPOD> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPMENTINFOPOD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  //dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShipmentInfoPOD> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPMENTINFOPOD,
    payload: axios.delete(requestUrl)
  });
  //dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

export const updateSearch = data => ({
  type: ACTION_TYPES.UPDATE_SEARCH,
  payload: data
});

export const resetForNew = () => ({
  type: ACTION_TYPES.RESET_FOR_NEW
});

export const negate = () => ({
  type: ACTION_TYPES.NEGATE_SHIPMENTINFORMATIONPOD
});
