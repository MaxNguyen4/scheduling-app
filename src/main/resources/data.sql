-- Insert a user with a hashed password (e.g., bcrypt hashed password)
INSERT INTO users (username, password)
VALUES ('max', '{noop}Test@123');

-- Insert events for the user with user_id = 1
INSERT INTO events (user_id, event_title, event_date, start_time, end_time, details)
VALUES 
(1, 'Project Kickoff Meeting', '2024-10-01', '09:00:00', '10:00:00', 'Discuss project scope and timeline'),
(1, 'Team Lunch', '2024-10-01', '12:00:00', '13:00:00', 'Team bonding over lunch'),
(1, 'Client Presentation', '2024-10-03', '15:00:00', '16:30:00', 'Present project progress to the client'),
(1, 'Code Review Session', '2024-10-05', '11:00:00', '12:00:00', 'Review the latest code submissions'),
(1, 'Design Workshop', '2024-10-07', '14:00:00', '16:00:00', 'Discuss new design ideas for the project'),
(1, 'Monthly Report Preparation', '2024-10-10', '10:00:00', '12:00:00', 'Prepare the monthly progress report'),
(1, 'Stakeholder Meeting', '2024-10-12', '09:00:00', '10:30:00', 'Meeting with stakeholders to discuss next steps'),
(1, 'Team Outing', '2024-10-14', '13:00:00', '18:00:00', 'Team outing to the beach'),
(1, 'QA Testing Phase', '2024-10-17', '09:00:00', '17:00:00', 'Full day dedicated to quality assurance testing'),
(1, 'Deployment Preparation', '2024-10-20', '10:00:00', '16:00:00', 'Prepare the application for deployment'),
(1, 'Final Deployment', '2024-10-22', '09:00:00', '11:00:00', 'Deploy the application to production'),
(1, 'Retrospective Meeting', '2024-10-25', '14:00:00', '15:30:00', 'Discuss lessons learned and improvements for next project'),
(1, 'Sprint Planning Session', '2024-09-02', '10:00:00', '11:00:00', 'Plan the tasks for the next sprint'),
(1, 'Team Breakfast', '2024-09-03', '08:30:00', '09:30:00', 'Team breakfast at a local cafe'),
(1, 'Client Requirements Review', '2024-09-05', '14:00:00', '15:30:00', 'Review the new requirements from the client'),
(1, 'Project Milestone Check', '2024-09-07', '09:00:00', '10:00:00', 'Check progress on project milestones'),
(1, 'Design Feedback Session', '2024-09-09', '11:00:00', '12:30:00', 'Get feedback on the latest design iterations'),
(1, 'Mid-Month Progress Review', '2024-09-15', '15:00:00', '16:00:00', 'Review progress at the mid-point of the month'),
(1, 'Client Demo', '2024-09-18', '13:00:00', '14:30:00', 'Demonstrate the latest project features to the client'),
(1, 'Team Building Activity', '2024-09-20', '14:00:00', '17:00:00', 'Outdoor team building games'),
(1, 'QA Bug Triage Meeting', '2024-09-24', '10:00:00', '11:30:00', 'Discuss and prioritize bugs found during QA');
