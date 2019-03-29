import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShipmentInfoPOD, defaultValue } from 'app/shared/model/shipment-info-pod.model';

export const ACTION_TYPES = {
  SEARCH_SHIPMENTINFOPODS: 'shipmentInfoPOD/SEARCH_SHIPMENTINFOPODS',
  FETCH_SHIPMENTINFOPOD_LIST: 'shipmentInfoPOD/FETCH_SHIPMENTINFOPOD_LIST',
  FETCH_SHIPMENTINFOPOD: 'shipmentInfoPOD/FETCH_SHIPMENTINFOPOD',
  CREATE_SHIPMENTINFOPOD: 'shipmentInfoPOD/CREATE_SHIPMENTINFOPOD',
  UPDATE_SHIPMENTINFOPOD: 'shipmentInfoPOD/UPDATE_SHIPMENTINFOPOD',
  DELETE_SHIPMENTINFOPOD: 'shipmentInfoPOD/DELETE_SHIPMENTINFOPOD',
  SET_BLOB: 'shipmentInfoPOD/SET_BLOB',
  RESET: 'shipmentInfoPOD/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShipmentInfoPOD>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
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
        errorMessage: action.payload
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
    default:
      return state;
  }
};

const apiUrl = 'api/shipment-info-pods';
const apiSearchUrl = 'api/_search/shipment-info-pods';

// Actions

export const getSearchEntities: ICrudSearchAction<IShipmentInfoPOD> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPMENTINFOPODS,
  payload: axios.get<IShipmentInfoPOD>(`${apiSearchUrl}?query=${query}`)
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
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShipmentInfoPOD> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPMENTINFOPOD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShipmentInfoPOD> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPMENTINFOPOD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
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
