package com.prt2121.github;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Modified from https://gist.github.com/dlew/33b650bd8ef3d360ff7d
 */
final class RxActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

  private static RxActivityLifecycleCallbacks instance;

  private Map<Activity, BehaviorSubject<RxActivityEvent>> activityBehaviorSubjectMap;

  public static RxActivityLifecycleCallbacks init(Application application) {
    if (instance == null) {
      instance = new RxActivityLifecycleCallbacks(application);
    }

    return instance;
  }

  public static RxActivityLifecycleCallbacks getInstance() {
    if (instance == null) {
      throw new IllegalStateException("RxActivityLifecycleCallbacks is not initialized!");
    }

    return instance;
  }

  private RxActivityLifecycleCallbacks(Application application) {
    activityBehaviorSubjectMap = new ConcurrentHashMap<>();
    application.registerActivityLifecycleCallbacks(this);
  }

  public Observable<RxActivityEvent> getLifecycle(Activity activity) {
    BehaviorSubject<RxActivityEvent> subject = activityBehaviorSubjectMap.get(activity);

    if (subject == null) {
      throw new IllegalStateException("The Activity is outside the lifecycle; cannot bind to it!");
    }

    return subject.asObservable();
  }

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    activityBehaviorSubjectMap.put(activity, BehaviorSubject.create(RxActivityEvent.CREATE));
  }

  @Override public void onActivityStarted(Activity activity) {
    activityBehaviorSubjectMap.get(activity).onNext(RxActivityEvent.START);
  }

  @Override public void onActivityResumed(Activity activity) {
    activityBehaviorSubjectMap.get(activity).onNext(RxActivityEvent.RESUME);
  }

  @Override public void onActivityPaused(Activity activity) {
    activityBehaviorSubjectMap.get(activity).onNext(RxActivityEvent.PAUSE);
  }

  @Override public void onActivityStopped(Activity activity) {
    activityBehaviorSubjectMap.get(activity).onNext(RxActivityEvent.STOP);
  }

  @Override public void onActivityDestroyed(Activity activity) {
    activityBehaviorSubjectMap.remove(activity).onNext(RxActivityEvent.DESTROY);
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    // Not tracked
  }
}