import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShipmentTracking, defaultValue } from 'app/shared/model/shipment-tracking.model';

export const ACTION_TYPES = {
  SEARCH_SHIPMENTTRACKINGS: 'shipmentTracking/SEARCH_SHIPMENTTRACKINGS',
  FETCH_SHIPMENTTRACKING_LIST: 'shipmentTracking/FETCH_SHIPMENTTRACKING_LIST',
  FETCH_SHIPMENTTRACKING: 'shipmentTracking/FETCH_SHIPMENTTRACKING',
  CREATE_SHIPMENTTRACKING: 'shipmentTracking/CREATE_SHIPMENTTRACKING',
  UPDATE_SHIPMENTTRACKING: 'shipmentTracking/UPDATE_SHIPMENTTRACKING',
  DELETE_SHIPMENTTRACKING: 'shipmentTracking/DELETE_SHIPMENTTRACKING',
  RESET: 'shipmentTracking/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShipmentTracking>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ShipmentTrackingState = Readonly<typeof initialState>;

// Reducer

export default (state: ShipmentTrackingState = initialState, action): ShipmentTrackingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SHIPMENTTRACKINGS):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTTRACKING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTTRACKING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SHIPMENTTRACKING):
    case REQUEST(ACTION_TYPES.UPDATE_SHIPMENTTRACKING):
    case REQUEST(ACTION_TYPES.DELETE_SHIPMENTTRACKING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SHIPMENTTRACKINGS):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTTRACKING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTTRACKING):
    case FAILURE(ACTION_TYPES.CREATE_SHIPMENTTRACKING):
    case FAILURE(ACTION_TYPES.UPDATE_SHIPMENTTRACKING):
    case FAILURE(ACTION_TYPES.DELETE_SHIPMENTTRACKING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SHIPMENTTRACKINGS):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTTRACKING_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTTRACKING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHIPMENTTRACKING):
    case SUCCESS(ACTION_TYPES.UPDATE_SHIPMENTTRACKING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHIPMENTTRACKING):
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

const apiUrl = 'api/shipment-trackings';
const apiSearchUrl = 'api/_search/shipment-trackings';

// Actions

export const getSearchEntities: ICrudSearchAction<IShipmentTracking> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPMENTTRACKINGS,
  payload: axios.get<IShipmentTracking>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IShipmentTracking> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTTRACKING_LIST,
    payload: axios.get<IShipmentTracking>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IShipmentTracking> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTTRACKING,
    payload: axios.get<IShipmentTracking>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IShipmentTracking> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHIPMENTTRACKING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShipmentTracking> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPMENTTRACKING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShipmentTracking> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPMENTTRACKING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
