import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShipmentMode, defaultValue } from 'app/shared/model/shipment-mode.model';

export const ACTION_TYPES = {
  SEARCH_SHIPMENTMODES: 'shipmentMode/SEARCH_SHIPMENTMODES',
  FETCH_SHIPMENTMODE_LIST: 'shipmentMode/FETCH_SHIPMENTMODE_LIST',
  FETCH_SHIPMENTMODE: 'shipmentMode/FETCH_SHIPMENTMODE',
  CREATE_SHIPMENTMODE: 'shipmentMode/CREATE_SHIPMENTMODE',
  UPDATE_SHIPMENTMODE: 'shipmentMode/UPDATE_SHIPMENTMODE',
  DELETE_SHIPMENTMODE: 'shipmentMode/DELETE_SHIPMENTMODE',
  RESET: 'shipmentMode/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShipmentMode>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ShipmentModeState = Readonly<typeof initialState>;

// Reducer

export default (state: ShipmentModeState = initialState, action): ShipmentModeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SHIPMENTMODES):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTMODE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTMODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SHIPMENTMODE):
    case REQUEST(ACTION_TYPES.UPDATE_SHIPMENTMODE):
    case REQUEST(ACTION_TYPES.DELETE_SHIPMENTMODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SHIPMENTMODES):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTMODE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTMODE):
    case FAILURE(ACTION_TYPES.CREATE_SHIPMENTMODE):
    case FAILURE(ACTION_TYPES.UPDATE_SHIPMENTMODE):
    case FAILURE(ACTION_TYPES.DELETE_SHIPMENTMODE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SHIPMENTMODES):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTMODE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTMODE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHIPMENTMODE):
    case SUCCESS(ACTION_TYPES.UPDATE_SHIPMENTMODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHIPMENTMODE):
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

const apiUrl = 'api/shipment-modes';
const apiSearchUrl = 'api/_search/shipment-modes';

// Actions

export const getSearchEntities: ICrudSearchAction<IShipmentMode> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPMENTMODES,
  payload: axios.get<IShipmentMode>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IShipmentMode> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SHIPMENTMODE_LIST,
  payload: axios.get<IShipmentMode>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IShipmentMode> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTMODE,
    payload: axios.get<IShipmentMode>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IShipmentMode> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHIPMENTMODE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShipmentMode> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPMENTMODE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShipmentMode> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPMENTMODE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
