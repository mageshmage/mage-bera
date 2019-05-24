import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity, ICrudSearchAction, ICrudGetAutoFillAction } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShipmentTracking, defaultValue } from 'app/shared/model/shipment-tracking.model';

export const ACTION_TYPES = {
  SEARCH_SHIPMENTTRACKINGS: 'shipmentTrackingSolo/SEARCH_SHIPMENTTRACKINGS',
  FETCH_SHIPMENTTRACKING_LIST: 'shipmentTrackingSolo/FETCH_SHIPMENTTRACKING_LIST',
  FETCH_SHIPMENTTRACKING: 'shipmentTrackingSolo/FETCH_SHIPMENTTRACKING',
  CREATE_SHIPMENTTRACKING: 'shipmentTrackingSolo/CREATE_SHIPMENTTRACKING',
  UPDATE_SHIPMENTTRACKING: 'shipmentTrackingSolo/UPDATE_SHIPMENTTRACKING',
  DELETE_SHIPMENTTRACKING: 'shipmentTrackingSolo/DELETE_SHIPMENTTRACKING',
  RESET: 'shipmentTrackingSolo/RESET',
  RESET_FOR_NEW: 'shipmentTrackingSolo/RESET_FOR_NEW',
  UPDATE_SEARCH: 'shipmentTrackingSolo/UPDATE_SEARCH',
  NEGATE_SHIPMENTTRACKING_ENABLE: 'shipmentTrackingSolo/NEGATE_SHIPMENTTRACKING_ENABLE',
  FETCH_SHIPMENTTRACKING_AUTOFILL: 'shipmentTrackingSolo/FETCH_SHIPMENTTRACKING_AUTOFILL'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShipmentTracking>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
  isEnable: false,
  search: ''
};

export type ShipmentTrackingSoloState = Readonly<typeof initialState>;

// Reducer

export default (state: ShipmentTrackingSoloState = initialState, action): ShipmentTrackingSoloState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SHIPMENTTRACKINGS):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTTRACKING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTTRACKING):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTTRACKING_AUTOFILL):
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
        updating: true,
        isEnable: false
      };
    case FAILURE(ACTION_TYPES.SEARCH_SHIPMENTTRACKINGS):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTTRACKING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTTRACKING):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTTRACKING_AUTOFILL):
    case FAILURE(ACTION_TYPES.CREATE_SHIPMENTTRACKING):
    case FAILURE(ACTION_TYPES.UPDATE_SHIPMENTTRACKING):
    case FAILURE(ACTION_TYPES.DELETE_SHIPMENTTRACKING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
        isEnable: false
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SHIPMENTTRACKINGS):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTTRACKING_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data,
        isEnable: true
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTTRACKING):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTTRACKING_AUTOFILL):
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
    case ACTION_TYPES.NEGATE_SHIPMENTTRACKING_ENABLE:
      return {
        ...state,
        isEnable: false,
        entity: {},
        entities: [],
        search: ''
      };
    default:
      return state;
  }
};

const apiUrl = 'api/shipment-trackings';
const apiSearchUrl = 'api/shipment-trackingssearch';
const apiAutoFillNewTracking = 'api/shipment-trackings-autoFillNewTracking';

// Actions

export const getSearchEntities: ICrudSearchAction<IShipmentTracking> = (query, vendorId, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPMENTTRACKINGS,
  payload: axios.get<IShipmentTracking>(
    `${apiSearchUrl}?query=${query}&vendorId=${vendorId}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`
  )
});

export const getEntities: ICrudGetAllAction<IShipmentTracking> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${`?page=${0}&size=${20}&sort=${'trackingDate,desc'}`}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTTRACKING_LIST,
    payload: axios.get<IShipmentTracking>(`${requestUrl}`)
  };
};

export const getEntity: ICrudGetAction<IShipmentTracking> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTTRACKING,
    payload: axios.get<IShipmentTracking>(requestUrl)
  };
};

export const autoFillNewTracking: ICrudGetAutoFillAction<IShipmentTracking> = (query, vendorId) => {
  const requestUrl = `${apiAutoFillNewTracking}?query=${query}&vendorId=${vendorId}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTTRACKING_AUTOFILL,
    payload: axios.get<IShipmentTracking>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IShipmentTracking> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHIPMENTTRACKING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  //dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShipmentTracking> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPMENTTRACKING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  //dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShipmentTracking> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPMENTTRACKING,
    payload: axios.delete(requestUrl)
  });
  //dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

export const resetForNew = () => ({
  type: ACTION_TYPES.RESET_FOR_NEW
});

export const negateEnable = () => ({
  type: ACTION_TYPES.NEGATE_SHIPMENTTRACKING_ENABLE
});

export const updateSearch = data => ({
  type: ACTION_TYPES.UPDATE_SEARCH,
  payload: data
});
