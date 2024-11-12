-- Insert a user with a hashed password (e.g., bcrypt hashed password)
INSERT INTO users (username, password)
VALUES ('max', 'Test@123');

-- Insert events for the user with user_id = 1
INSERT INTO events (user_id, event_title, event_date, start_time, end_time, details)
VALUES 
(1, 'Project Kickoff Meeting', '2024-11-01', '09:00:00', '10:00:00', 'Discuss project scope and timeline'),
(1, 'Team Lunch', '2024-11-01', '12:00:00', '13:00:00', 'Team bonding over lunch'),
(1, 'Client Presentation', '2024-11-03', '15:00:00', '16:30:00', 'Present project progress to the client'),
(1, 'Code Review Session', '2024-11-05', '11:00:00', '12:00:00', 'Review the latest code submissions'),
(1, 'Design Workshop', '2024-11-07', '14:00:00', '16:00:00', 'Discuss new design ideas for the project'),
(1, 'Monthly Report Preparation', '2024-11-10', '10:00:00', '12:00:00', 'Prepare the monthly progress report'),
(1, 'Team Outing', '2024-11-14', '17:56:00', '18:00:00', 'Team outing to the beach'),
(1, 'Clash!', '2024-11-14', '17:00:00', '19:00:00', 'Clash event!'),
(1, 'QA Testing Phase', '2024-11-17', '09:00:00', '17:00:00', 'Full day dedicated to quality assurance testing'),
(1, 'Deployment Preparation', '2024-11-20', '10:00:00', '16:00:00', 'Prepare the application for deployment'),
(1, 'Final Deployment', '2024-11-22', '09:00:00', '11:00:00', 'Deploy the application to production'),
(1, 'Retrospective Meeting', '2024-11-25', '14:00:00', '15:30:00', 'Discuss lessons learned and improvements for next project'),
(1, 'Sprint Planning Session', '2024-12-02', '10:00:00', '11:00:00', 'Plan the tasks for the next sprint'),
(1, 'Team Breakfast', '2024-12-03', '08:30:00', '09:30:00', 'Team breakfast at a local cafe'),
(1, 'Client Requirements Review', '2024-12-05', '14:00:00', '15:30:00', 'Review the new requirements from the client'),
(1, 'Project Milestone Check', '2024-12-07', '09:00:00', '10:00:00', 'Check progress on project milestones'),
(1, 'Design Feedback Session', '2024-12-09', '11:00:00', '12:30:00', 'Get feedback on the latest design iterations'),
(1, 'Mid-Month Progress Review', '2024-12-15', '15:00:00', '16:00:00', 'Review progress at the mid-point of the month'),
(1, 'Client Demo', '2024-12-18', '13:00:00', '14:30:00', 'Demonstrate the latest project features to the client'),
(1, 'Team Building Activity', '2024-12-20', '14:00:00', '17:00:00', 'Outdoor team building games'),
(1, 'QA Bug Triage Meeting', '2024-12-24', '10:00:00', '11:30:00', 'Discuss and prioritize bugs found during QA'),
(1, 'Morning Standup', '2024-11-12', '09:30:00', '10:00:00', 'Daily team sync-up'),
(1, 'Design Review', '2024-11-12', '10:00:00', '11:00:00', 'Review design progress with the team'),
(1, 'Product Demo', '2024-11-12', '10:30:00', '11:30:00', 'Demo the new product feature'),
(1, 'Lunch with Client', '2024-11-12', '12:00:00', '13:00:00', 'Discuss project over lunch'),
(1, 'Code Debugging Session', '2024-11-12', '11:00:00', '12:30:00', 'Resolve critical bugs'),
(1, 'Afternoon Workshop', '2024-11-12', '13:30:00', '15:00:00', 'Hands-on workshop with the team'),
(1, 'Clash Meeting', '2024-11-12', '14:00:00', '15:30:00', 'Overlapping event to test clash handling');