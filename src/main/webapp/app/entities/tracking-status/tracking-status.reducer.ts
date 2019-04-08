import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity, ICrudGetAllActionByVendor } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITrackingStatus, defaultValue } from 'app/shared/model/tracking-status.model';

export const ACTION_TYPES = {
  SEARCH_TRACKINGSTATUSES: 'trackingStatus/SEARCH_TRACKINGSTATUSES',
  FETCH_TRACKINGSTATUS_LIST: 'trackingStatus/FETCH_TRACKINGSTATUS_LIST',
  FETCH_TRACKINGSTATUS: 'trackingStatus/FETCH_TRACKINGSTATUS',
  CREATE_TRACKINGSTATUS: 'trackingStatus/CREATE_TRACKINGSTATUS',
  UPDATE_TRACKINGSTATUS: 'trackingStatus/UPDATE_TRACKINGSTATUS',
  DELETE_TRACKINGSTATUS: 'trackingStatus/DELETE_TRACKINGSTATUS',
  RESET: 'trackingStatus/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITrackingStatus>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TrackingStatusState = Readonly<typeof initialState>;

// Reducer

export default (state: TrackingStatusState = initialState, action): TrackingStatusState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TRACKINGSTATUSES):
    case REQUEST(ACTION_TYPES.FETCH_TRACKINGSTATUS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TRACKINGSTATUS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TRACKINGSTATUS):
    case REQUEST(ACTION_TYPES.UPDATE_TRACKINGSTATUS):
    case REQUEST(ACTION_TYPES.DELETE_TRACKINGSTATUS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TRACKINGSTATUSES):
    case FAILURE(ACTION_TYPES.FETCH_TRACKINGSTATUS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TRACKINGSTATUS):
    case FAILURE(ACTION_TYPES.CREATE_TRACKINGSTATUS):
    case FAILURE(ACTION_TYPES.UPDATE_TRACKINGSTATUS):
    case FAILURE(ACTION_TYPES.DELETE_TRACKINGSTATUS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TRACKINGSTATUSES):
    case SUCCESS(ACTION_TYPES.FETCH_TRACKINGSTATUS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TRACKINGSTATUS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TRACKINGSTATUS):
    case SUCCESS(ACTION_TYPES.UPDATE_TRACKINGSTATUS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TRACKINGSTATUS):
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

const apiUrl = 'api/tracking-statuses';
const apiUrlByVendor = 'api/tracking-statuses-byvendor';
const apiSearchUrl = 'api/_search/tracking-statuses';

// Actions

export const getSearchEntities: ICrudSearchAction<ITrackingStatus> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TRACKINGSTATUSES,
  payload: axios.get<ITrackingStatus>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ITrackingStatus> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TRACKINGSTATUS_LIST,
  payload: axios.get<ITrackingStatus>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getTrackingStatusByVendorId: ICrudGetAllActionByVendor<ITrackingStatus> = (vendorId, page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TRACKINGSTATUS_LIST,
  payload: axios.get<ITrackingStatus>(`${apiUrlByVendor}/${vendorId}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITrackingStatus> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TRACKINGSTATUS,
    payload: axios.get<ITrackingStatus>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITrackingStatus> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TRACKINGSTATUS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITrackingStatus> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TRACKINGSTATUS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITrackingStatus> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TRACKINGSTATUS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
