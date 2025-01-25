import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RhManagementComponent } from './rh-management.component';

describe('RhManagementComponent', () => {
  let component: RhManagementComponent;
  let fixture: ComponentFixture<RhManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RhManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RhManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
